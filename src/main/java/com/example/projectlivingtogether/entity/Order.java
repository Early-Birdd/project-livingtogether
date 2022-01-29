package com.example.projectlivingtogether.entity;

import com.example.projectlivingtogether.enumclass.OrderStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class Order extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "order_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private LocalDateTime orderDate;

    public void addOrderItem(OrderItem orderItem){

        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public static Order createOrder(Member member, List<OrderItem> orderItemList){

        Order order = new Order();
        order.setMember(member);

        for(OrderItem orderItem : orderItemList){

            order.addOrderItem(orderItem);
        }

        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());

        return order;
    }

    public int getTotalPrice(){

        int totalPrice = 0;

        for(OrderItem orderItem : orderItems){

            totalPrice += orderItem.getTotalPrice();
        }

        return totalPrice;
    }
    public void cancelOrder(){

        this.orderStatus = OrderStatus.CANCEL;

        for(OrderItem orderItem : orderItems){

            orderItem.cancel();
        }
    }
}
