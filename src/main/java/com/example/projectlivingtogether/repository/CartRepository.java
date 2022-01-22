package com.example.projectlivingtogether.repository;

import com.example.projectlivingtogether.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Long> {
}
