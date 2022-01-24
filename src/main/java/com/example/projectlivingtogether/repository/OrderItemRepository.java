package com.example.projectlivingtogether.repository;

import com.example.projectlivingtogether.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
