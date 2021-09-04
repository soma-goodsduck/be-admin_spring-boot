package com.ducks.goodsduck.admin.model.entity.Image;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "image_type")
public class Image {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "image_id")
    private Long id;
    private String originName;
    private String uploadName;
    private String url;

    public Image(String originName, String uploadName, String url) {
        this.originName = originName;
        this.uploadName = uploadName;
        this.url = url;
    }
}
