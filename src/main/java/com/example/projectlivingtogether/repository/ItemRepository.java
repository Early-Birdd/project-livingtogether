package com.example.projectlivingtogether.repository;

import com.example.projectlivingtogether.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long> {

    List<Item> findByItemName(String itemName);
    List<Item> findByPriceGreaterThan(Integer price);
    List<Item> findByPriceGreaterThanOrderByPriceDesc(Integer price);
    List<Item> findByItemNameOrItemDetail(String itemName, String itemDetail);
}
