package com.example.projectlivingtogether.entity;

import com.example.projectlivingtogether.enumclass.ItemStatus;
import com.example.projectlivingtogether.dto.ItemDto;
import com.example.projectlivingtogether.exception.OutOfQuantityException;
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

    public void updateItem(ItemDto itemDto){

        this.itemStatus = itemDto.getItemStatus();
        this.price = itemDto.getPrice();
        this.itemQuantity = itemDto.getItemQuantity();
        this.itemName = itemDto.getItemName();
        this.itemDetail = itemDto.getItemDetail();
    }

    public void removeQuantity(int itemQuantity){

        int restQuantity = this.itemQuantity - itemQuantity;

        if(restQuantity < 0){

            throw new OutOfQuantityException("재고가 부족합니다. (현재 재고 수량 : " + this.itemQuantity + ")");
        }

        this.itemQuantity = restQuantity;
    }

    public void addItemQuantity(int itemQuantity){

        this.itemQuantity += itemQuantity;
    }
}
