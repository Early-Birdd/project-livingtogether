package com.example.projectlivingtogether.dto;

import com.example.projectlivingtogether.entity.Order;
import com.example.projectlivingtogether.enumclass.OrderStatus;
import lombok.Data;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
public class OrderHistoryDto {

    private Long orderId;

    private OrderStatus orderStatus;

    private String orderDate;

    public OrderHistoryDto(Order order) {

        this.orderId = order.getId();
        this.orderStatus = order.getOrderStatus();
        this.orderDate = order.getOrderDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
    }

    private List<OrderItemDto> orderItemDtoList = new ArrayList<>();

    public void addOrderItemDto(OrderItemDto orderItemDto){

        orderItemDtoList.add(orderItemDto);
    }
}
