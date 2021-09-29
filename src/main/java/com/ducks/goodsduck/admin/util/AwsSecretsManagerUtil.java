package com.ducks.goodsduck.admin.util;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.secretsmanager.AWSSecretsManager;
import com.amazonaws.services.secretsmanager.AWSSecretsManagerClientBuilder;
import com.amazonaws.services.secretsmanager.model.*;
import org.json.JSONObject;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.security.InvalidParameterException;
import java.util.Base64;

public class AwsSecretsManagerUtil {

    public static JSONObject getSecret() {

        String accessKeySecretManager = PropertyUtil.getProperty("cloud.aws.credentials.accessKeySecretManager");
        String secretKeySecretManager = PropertyUtil.getProperty("cloud.aws.credentials.secretKeySecretManager");
        String secretName = "goodsduck/admin";
        String region = "ap-northeast-2";

        // Create a Secrets Manager client
        AWSCredentials awsCredentials = new BasicAWSCredentials(accessKeySecretManager, secretKeySecretManager);
        AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(region)
                .build();

        // In this sample we only handle the specific exceptions for the 'GetSecretValue' API.
        // See https://docs.aws.amazon.com/secretsmanager/latest/apireference/API_GetSecretValue.html
        // We rethrow the exception by default.
        String secret, decodedBinarySecret, awsSecret;
        GetSecretValueRequest getSecretValueRequest = new GetSecretValueRequest()
                .withSecretId(secretName);
        GetSecretValueResult getSecretValueResult = null;

        try {
            getSecretValueResult = client.getSecretValue(getSecretValueRequest);
        } catch (DecryptionFailureException e) {
            // Secrets Manager can't decrypt the protected secret text using the provided KMS key.
            // Deal with the exception here, and/or rethrow at your discretion.
            throw e;
        } catch (InternalServiceErrorException e) {
            // An error occurred on the server side.
            // Deal with the exception here, and/or rethrow at your discretion.
            throw e;
        } catch (InvalidParameterException e) {
            // You provided an invalid value for a parameter.
            // Deal with the exception here, and/or rethrow at your discretion.
            throw e;
        } catch (InvalidRequestException e) {
            // You provided a parameter value that is not valid for the current state of the resource.
            // Deal with the exception here, and/or rethrow at your discretion.
            throw e;
        } catch (ResourceNotFoundException e) {
            // We can't find the resource that you asked for.
            // Deal with the exception here, and/or rethrow at your discretion.
            throw e;
        }

        // Decrypts secret using the associated KMS CMK.
        // Depending on whether the secret is a string or binary, one of these fields will be populated.
        if (getSecretValueResult.getSecretString() != null) {
            secret = getSecretValueResult.getSecretString();
            awsSecret = secret;
        }
        else {
            decodedBinarySecret = new String(Base64.getDecoder().decode(getSecretValueResult.getSecretBinary()).array());
            awsSecret = decodedBinarySecret;
        }

        return new JSONObject(awsSecret);
    }
}
