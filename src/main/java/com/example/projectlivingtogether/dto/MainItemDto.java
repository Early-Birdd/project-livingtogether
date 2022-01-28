package com.example.projectlivingtogether.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Data;

@Data
public class MainItemDto {

    private Long id;

    private Integer price;

    private String itemName;
    private String itemDetail;
    private String imageUrl;

    @QueryProjection
    public MainItemDto(Long id, Integer price, String itemName, String itemDetail, String imageUrl) {

        this.id = id;
        this.price = price;
        this.itemName = itemName;
        this.itemDetail = itemDetail;
        this.imageUrl = imageUrl;
    }
}
