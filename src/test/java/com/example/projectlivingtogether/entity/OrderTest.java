package com.example.projectlivingtogether.entity;

import com.example.projectlivingtogether.enumclass.ItemStatus;
import com.example.projectlivingtogether.repository.ItemRepository;
import com.example.projectlivingtogether.repository.MemberRepository;
import com.example.projectlivingtogether.repository.OrderItemRepository;
import com.example.projectlivingtogether.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
@Transactional
public class OrderTest {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    OrderItemRepository orderItemRepository;

    @PersistenceContext
    EntityManager entityManager;

    public Item createItem(){

        Item item = new Item();
        item.setItemName("create 테스트 상품");
        item.setItemStatus(ItemStatus.SALE);
        item.setItemQuantity(500);
        item.setPrice(50000);
        item.setItemDetail("item 상세");
        item.setCreatedDate(LocalDateTime.now());
        item.setUpdatedDate(LocalDateTime.now());

        return item;
    }

    @Test
    @DisplayName("cascade 테스트")
    public void cascadeTest(){

        Order order = new Order();

        for(int i = 0; i < 5; i++){

            Item item = this.createItem();
            itemRepository.save(item);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setItem(item);
            orderItem.setOrderPrice(30000);
            orderItem.setCount(30);
            orderItem.setCreatedDate(LocalDateTime.now());
            orderItem.setUpdatedDate(LocalDateTime.now());

            order.getOrderItems().add(orderItem);
        }

        orderRepository.saveAndFlush(order);
        entityManager.clear();

        Order savedOrder = orderRepository.findById(order.getId()).orElseThrow(EntityNotFoundException::new);
        Assertions.assertThat(savedOrder.getOrderItems().size()).isEqualTo(5);
    }

    public Order createOrder(){

        Order order = new Order();

        for(int i = 0; i < 5; i++){

            Item item = this.createItem();
            itemRepository.save(item);

            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setItem(item);
            orderItem.setOrderPrice(30000);
            orderItem.setCount(30);
            orderItem.setCreatedDate(LocalDateTime.now());
            orderItem.setUpdatedDate(LocalDateTime.now());

            order.getOrderItems().add(orderItem);
        }

        Member member = new Member();
        memberRepository.save(member);

        order.setMember(member);
        orderRepository.save(order);

        return order;
    }

    @Test
    @DisplayName("고아 객체 제거")
    public void orphanRemovalTest(){

        Order order = this.createOrder();
        order.getOrderItems().remove(2);

        entityManager.flush();
    }

    @Test
    @DisplayName("지연 로딩")
    public void lazyLoadingTest(){

        Order order = this.createOrder();
        Long orderItemId = order.getOrderItems().get(2).getId();

        entityManager.flush();
        entityManager.clear();

        OrderItem orderItem = orderItemRepository.findById(orderItemId).orElseThrow(EntityNotFoundException::new);
    }
}
