package com.example.projectlivingtogether.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "item_image")
public class ItemImage extends BaseEntity{

    @Id
    @Column(name = "item_image_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String imageName;
    private String originImageName;
    private String imageUrl;
    private String rpImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    public void updateImage(String imageName, String originImageName, String imageUrl){

        this.imageName = imageName;
        this.originImageName = originImageName;
        this.imageUrl = imageUrl;
    }
}
