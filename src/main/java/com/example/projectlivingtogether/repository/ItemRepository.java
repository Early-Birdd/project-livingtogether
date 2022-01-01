package com.example.projectlivingtogether.repository;

import com.example.projectlivingtogether.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemRepository extends JpaRepository<Item, Long>, QuerydslPredicateExecutor<Item> {

    List<Item> findByItemName(String itemName);
    List<Item> findByPriceGreaterThan(Integer price);
    List<Item> findByPriceGreaterThanOrderByPriceDesc(Integer price);
    List<Item> findByItemNameOrItemDetail(String itemName, String itemDetail);

    @Query("select a from Item a where a.itemDetail like %:itemDetail% order by a.price desc")
    List<Item> findByItemDetail(@Param("itemDetail") String itemDetail);
}
