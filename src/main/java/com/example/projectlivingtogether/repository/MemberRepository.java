package com.example.projectlivingtogether.repository;

import com.example.projectlivingtogether.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByEmail(String email);
}
