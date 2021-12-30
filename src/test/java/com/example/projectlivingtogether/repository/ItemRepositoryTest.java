package com.example.projectlivingtogether.repository;

import com.example.projectlivingtogether.ItemStatus;
import com.example.projectlivingtogether.entity.Item;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @Test
    @DisplayName("상품 create")
    public void createItem(){

        Item item = new Item();

        item.setItemName("create 테스트 상품");
        item.setItemStatus(ItemStatus.SALE);
        item.setItemQuantity(500);
        item.setPrice(50000);
        item.setItemDetail("create 테스트 상세");
        item.setCreatedDate(LocalDateTime.now());
        item.setUpdatedDate(LocalDateTime.now());

        Item createdItem = itemRepository.save(item);
        System.out.println(createdItem.toString());
    }

    public void createItemList(){

        for(int i = 1; i <= 20; i++){

            Item item = new Item();

            item.setItemName("find 테스트 상품" + i);
            item.setItemStatus(ItemStatus.SALE);
            item.setItemQuantity(500 + i);
            item.setPrice(50000 + (100 * i));
            item.setItemDetail("find 테스트 상세" + i);
            item.setCreatedDate(LocalDateTime.now());
            item.setUpdatedDate(LocalDateTime.now());

            Item createdItem = itemRepository.save(item);
        }
    }

    @Test
    @DisplayName("상품 find")
    public void findByItemNameTest(){

        this.createItemList();
        List<Item> itemList = itemRepository.findByItemName("find 테스트 상품1");

        for(Item item : itemList){

            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("Price GreaterThan")
    public void findByPriceGreaterThanTest(){

        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceGreaterThan(51500);

        for(Item item : itemList){

            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("Price GreaterThan Desc")
    public void findByPriceGreaterThanOrderByPriceDescTest(){

        this.createItemList();
        List<Item> itemList = itemRepository.findByPriceGreaterThanOrderByPriceDesc(51500);

        for(Item item : itemList){

            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("상품, 설명 find")
    public void findByItemNameOrItemDetailTest(){

        this.createItemList();
        List<Item> itemList = itemRepository.findByItemNameOrItemDetail("find 테스트 상품20", "find 테스트 상세15");

        for(Item item : itemList){

            System.out.println(item.toString());
        }
    }

}