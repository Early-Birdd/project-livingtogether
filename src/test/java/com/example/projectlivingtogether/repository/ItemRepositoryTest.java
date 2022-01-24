package com.example.projectlivingtogether.repository;

import com.example.projectlivingtogether.ItemStatus;
import com.example.projectlivingtogether.entity.Item;
import com.example.projectlivingtogether.entity.QItem;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
class ItemRepositoryTest {

    @Autowired
    ItemRepository itemRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    @DisplayName("상품 생성")
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

        for(int i = 0; i < 10; i++){

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

        for(int i = 10; i < 20; i++){

            Item item = new Item();

            item.setItemName("find 테스트 상품" + i);
            item.setItemStatus(ItemStatus.SOLD_OUT);
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
        List<Item> itemList = itemRepository.findByItemNameOrItemDetail("find 테스트 상품19", "find 테스트 상세15");

        for(Item item : itemList){

            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("@Query, 지정 설명 포함한 상품 가격 desc find")
    public void findByItemDetailTest(){

        this.createItemList();
        List<Item> itemList = itemRepository.findByItemDetail("find 테스트 상세");

        for(Item item : itemList){

            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("Querydsl test")
    public void queryDslTest(){

        this.createItemList();
        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(entityManager);

        QItem qItem = QItem.item;

        JPAQuery<Item> itemJPAQuery = jpaQueryFactory.selectFrom(qItem)
                .where(qItem.itemName.like("%" + "테스트" + "%"))
                .orderBy(qItem.price.desc());

        List<Item> itemList = itemJPAQuery.fetch();

        for(Item item : itemList){

            System.out.println(item.toString());
        }
    }

    @Test
    @DisplayName("Querydsl test2 - Predicate")
    public void queryDslTest2(){

        this.createItemList();
        BooleanBuilder booleanBuilder = new BooleanBuilder();

        QItem qqItem = QItem.item;

        String itemStatus = "SOLD_OUT";
        String itemName = "테스트 상품";
        int price = 51700;

        if(StringUtils.equals(itemStatus, ItemStatus.SOLD_OUT)){
            booleanBuilder.and(qqItem.itemStatus.eq(ItemStatus.SOLD_OUT));
        }
        booleanBuilder.and(qqItem.itemName.like("%" + itemName + "%"));
        booleanBuilder.and(qqItem.price.gt(price));

        Pageable pageable = PageRequest.of(0,1);
        Page<Item> itemPage = itemRepository.findAll(booleanBuilder, pageable);
        System.out.println("total : " + itemPage.getTotalElements());

        List<Item> itemList = itemPage.getContent();

        for(Item item : itemList){

            System.out.println(item.toString());
        }
    }
}