package com.example.projectlivingtogether.dto;

import lombok.Data;

@Data
public class CartDetailDto {

    private Long cartItemId;

    private int price;
    private int count;

    private String itemName;
    private String imageUrl;

    public CartDetailDto(Long cartItemId, int price, int count, String itemName, String imageUrl) {

        this.cartItemId = cartItemId;
        this.price = price;
        this.count = count;
        this.itemName = itemName;
        this.imageUrl = imageUrl;
    }
}
