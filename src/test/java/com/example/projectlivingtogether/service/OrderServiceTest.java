package com.example.projectlivingtogether.service;

import com.example.projectlivingtogether.dto.OrderDto;
import com.example.projectlivingtogether.entity.Item;
import com.example.projectlivingtogether.entity.Member;
import com.example.projectlivingtogether.entity.Order;
import com.example.projectlivingtogether.entity.OrderItem;
import com.example.projectlivingtogether.enumclass.ItemStatus;
import com.example.projectlivingtogether.enumclass.OrderStatus;
import com.example.projectlivingtogether.repository.ItemRepository;
import com.example.projectlivingtogether.repository.MemberRepository;
import com.example.projectlivingtogether.repository.OrderRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
@Transactional
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    MemberRepository memberRepository;

    public Item saveItem(){

        Item item = new Item();
        item.setItemStatus(ItemStatus.SALE);
        item.setPrice(50000);
        item.setItemQuantity(50);
        item.setItemName("테스트");
        item.setItemDetail("테스트 상세");

        return itemRepository.save(item);
    }

    public Member saveMember(){

        Member member = new Member();
        member.setEmail("member@test.mail");

        return memberRepository.save(member);
    }

    @Test
    @DisplayName("주문")
    public void order(){

        Item item = saveItem();

        Member member = saveMember();

        OrderDto orderDto = new OrderDto();
        orderDto.setItemId(item.getId());
        orderDto.setCount(30);

        Long orderId = orderService.order(orderDto, member.getEmail());
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);

        List<OrderItem> orderItems = order.getOrderItems();

        int totalPrice = orderDto.getCount() * item.getPrice();

        Assertions.assertThat(totalPrice).isEqualTo(order.getTotalPrice());
    }

    @Test
    @DisplayName("주문 취소")
    public void cancelOrder(){

        Item item = saveItem();

        Member member = saveMember();

        OrderDto orderDto = new OrderDto();
        orderDto.setItemId(item.getId());
        orderDto.setCount(30);

        Long orderId = orderService.order(orderDto, member.getEmail());
        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        orderService.cancelOrder(orderId);

        Assertions.assertThat(OrderStatus.CANCEL).isEqualTo(order.getOrderStatus());
        Assertions.assertThat(50).isEqualTo(item.getItemQuantity());
    }
}
