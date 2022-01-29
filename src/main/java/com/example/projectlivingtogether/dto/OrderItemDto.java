package com.example.projectlivingtogether.dto;

import com.example.projectlivingtogether.entity.OrderItem;
import lombok.Data;

@Data
public class OrderItemDto {

    private String itemName;

    private int orderPrice;

    private int count;

    private String imageUrl;

    public OrderItemDto(OrderItem orderItem, String imageUrl) {

        this.itemName = orderItem.getItem().getItemName();
        this.orderPrice = orderItem.getOrderPrice();
        this.count = orderItem.getCount();
        this.imageUrl = imageUrl;
    }
}
