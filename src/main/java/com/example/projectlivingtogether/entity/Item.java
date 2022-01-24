package com.example.projectlivingtogether.entity;

import com.example.projectlivingtogether.ItemStatus;
import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "item")
public class Item extends BaseEntity{

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false)
    private Integer itemQuantity;

    @Column(nullable = false)
    private String itemName;

    @Lob
    @Column(nullable = false)
    private String itemDetail;
}
