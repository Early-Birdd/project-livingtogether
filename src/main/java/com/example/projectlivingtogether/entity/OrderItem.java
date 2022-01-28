package com.example.projectlivingtogether.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "order_item")
public class OrderItem extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    private int orderPrice;
    private int orderQuantity;

    public static OrderItem createOrderItem(Item item, int orderQuantity){

        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderQuantity(orderQuantity);
        orderItem.setOrderPrice(item.getPrice());

        item.removeQuantity(orderQuantity);

        return orderItem;
    }

    public int getTotalPrice(){

        return orderPrice * orderQuantity;
    }
}
