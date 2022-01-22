package com.example.projectlivingtogether.entity;

import com.example.projectlivingtogether.ItemStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "item")
public class Item {

    @Id
    @Column(name = "item_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;

    @Column(nullable = false)
    private int price;

    @Column(nullable = false)
    private int itemQuantity;

    @Column(nullable = false)
    private String itemName;

    @Lob
    @Column(nullable = false)
    private String itemDetail;

    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
