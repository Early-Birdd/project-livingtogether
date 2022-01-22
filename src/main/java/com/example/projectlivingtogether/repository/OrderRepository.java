package com.example.projectlivingtogether.repository;

import com.example.projectlivingtogether.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
