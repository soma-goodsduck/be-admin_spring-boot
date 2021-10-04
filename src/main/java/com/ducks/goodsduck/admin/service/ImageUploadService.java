package com.ducks.goodsduck.admin.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.ducks.goodsduck.admin.model.entity.Image.Image;
import com.ducks.goodsduck.admin.model.enums.ImageType;
import com.ducks.goodsduck.admin.util.AwsSecretsManagerUtil;
import com.ducks.goodsduck.admin.util.PropertyUtil;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.imgscalr.Scalr;
import org.json.JSONObject;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ImageUploadService {

    private AmazonS3 s3Client;
    private static final JSONObject secret = AwsSecretsManagerUtil.getSecret();

    private static String accessKeyS3 = secret.getString("cloud.aws.credentials.accessKeyS3");
    private static String secretKeyS3 = secret.getString("cloud.aws.credentials.secretKeyS3");
    private static String region = secret.getString("cloud.aws.region.static");
    private static String itemS3Bucket = secret.getString("cloud.aws.s3.itemBucket");
    private static String profileS3Bucket = secret.getString("cloud.aws.s3.profileBucket");
    private static String chatS3Bucket = secret.getString("cloud.aws.s3.chatBucket");
    private static String postS3Bucket = secret.getString("cloud.aws.s3.postBucket");

    @PostConstruct
    public void setS3Client() {
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKeyS3, secretKeyS3);
        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(region)
                .build();
    }

    public Boolean deleteImage(Image image, ImageType imageType) {

        String uploadName = image.getUploadName();

        try {
            if(imageType.equals(ImageType.ITEM)) {
                boolean isExistObject = s3Client.doesObjectExist(itemS3Bucket, uploadName);
                if (isExistObject) {
                    s3Client.deleteObject(itemS3Bucket, uploadName);
                }
            } else if(imageType.equals(ImageType.PROFILE)) {
                boolean isExistObject = s3Client.doesObjectExist(profileS3Bucket, uploadName);
                if (isExistObject) {
                    s3Client.deleteObject(profileS3Bucket, uploadName);
                }
            } else if(imageType.equals(ImageType.CHAT)) {
                boolean isExistObject = s3Client.doesObjectExist(chatS3Bucket, uploadName);
                if (isExistObject) {
                    s3Client.deleteObject(chatS3Bucket, uploadName);
                }
            }else if(imageType.equals(ImageType.POST)) {
                boolean isExistObject = s3Client.doesObjectExist(postS3Bucket, uploadName);
                if (isExistObject) {
                    s3Client.deleteObject(postS3Bucket, uploadName);
                }
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<Image> uploadImages(List<MultipartFile> multipartFiles) throws IOException, ImageProcessingException, MetadataException {

        List<Image> images = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            if(!multipartFile.isEmpty()) {
                Image image = uploadImage(multipartFile);
                if(image != null) {
                    images.add(image);
                }
            }
        }

        return images;
    }

    /** S3에 이미지 업로드 **/
    public Image uploadImage(MultipartFile multipartFile) throws IOException, ImageProcessingException, MetadataException {

        // S3 셋팅
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKeyS3, secretKeyS3);
        s3Client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(region)
                .build();

        if(multipartFile.isEmpty()) {
            return null;
        }

        String originName = multipartFile.getOriginalFilename();
        String uploadName = createUploadName(originName);
        String EXT = extractExt(originName);
        String ext = EXT.toLowerCase();

        BufferedImage image = ImageIO.read(multipartFile.getInputStream());

        // FEAT : 파일 회전 체크
        int orientation = 1;
        Metadata metadata = ImageMetadataReader.readMetadata(multipartFile.getInputStream());
        ExifIFD0Directory directory = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);
        if(directory != null && directory.getTagCount() != 0 && directory.containsTag(ExifIFD0Directory.TAG_ORIENTATION)) {
            orientation = directory.getInt(ExifIFD0Directory.TAG_ORIENTATION);
        }

        switch (orientation) {
            case 6:
                image = Scalr.rotate(image, Scalr.Rotation.CW_90, null);
                break;
            case 3:
                image = Scalr.rotate(image, Scalr.Rotation.CW_180, null);
                break;
            case 8:
                image = Scalr.rotate(image, Scalr.Rotation.CW_270, null);
                break;
            default:
                break;
        }

        uploadImageToS3(s3Client, uploadName, ext, image);

        return new Image(originName, uploadName, s3Client.getUrl(itemS3Bucket, uploadName).toString());
    }

    private void uploadImageToS3(AmazonS3 s3Client, String uploadName, String ext, BufferedImage image) throws IOException {

        ByteArrayOutputStream imageOS = new ByteArrayOutputStream();
        ImageIO.write(image, ext, imageOS);

        InputStream imageIS = new ByteArrayInputStream(imageOS.toByteArray());

        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(imageOS.size());

        if (ext.equals("jpg")) {
            metadata.setContentType("jpg");
        } else if (ext.equals("jpeg")) {
            metadata.setContentType("jpeg");
        } else if (ext.equals("png")) {
            metadata.setContentType("png");
        } else if (ext.equals("gif")) {
            metadata.setContentType("gif");
        }

        try {
            s3Client.putObject(new PutObjectRequest(itemS3Bucket, uploadName, imageIS, metadata));
        } catch (Exception e) {
            throw new RuntimeException("Fail to upload image in ImageUploadService.uploadImageToS3");
        }
    }

    private String createUploadName(String originName) {

        // 업로드 파일명 (중복때문에 클라이언트에서 보낸 파일 이름이랑 다르게)
        String uuid = UUID.randomUUID().toString();

        // .png .img .jpeg
        String ext = extractExt(originName);

        return uuid + "." + ext;
    }

    private String extractExt(String originName) {

        int idx = originName.lastIndexOf(".");
        String ext = originName.substring(idx + 1);

        return ext;
    }
}
