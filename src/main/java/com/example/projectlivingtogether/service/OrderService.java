package com.example.projectlivingtogether.service;

import com.example.projectlivingtogether.dto.OrderDto;
import com.example.projectlivingtogether.entity.Item;
import com.example.projectlivingtogether.entity.Member;
import com.example.projectlivingtogether.entity.Order;
import com.example.projectlivingtogether.entity.OrderItem;
import com.example.projectlivingtogether.repository.ItemRepository;
import com.example.projectlivingtogether.repository.MemberRepository;
import com.example.projectlivingtogether.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;

    public Long order(OrderDto orderDto, String email){

        Item item = itemRepository.findById(orderDto.getItemId()).orElseThrow(EntityNotFoundException::new);

        Member member = memberRepository.findByEmail(email);

        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = OrderItem.createOrderItem(item, orderDto.getOrderQuantity());
        orderItemList.add(orderItem);

        Order order = Order.createOrder(member, orderItemList);
        orderRepository.save(order);

        return order.getId();
    }
}
