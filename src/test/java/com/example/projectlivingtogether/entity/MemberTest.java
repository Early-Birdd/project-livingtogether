package com.example.projectlivingtogether.entity;

import com.example.projectlivingtogether.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
@Transactional
public class MemberTest {

    @Autowired
    MemberRepository memberRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    @DisplayName("Member Auditing")
    @WithMockUser(username = "User Korean", roles = "USER")
    public void memberAuditingTest(){

        Member testMember = new Member();
        memberRepository.save(testMember);

        entityManager.flush();
        entityManager.clear();

        Member member = memberRepository.findById(testMember.getId()).orElseThrow(EntityNotFoundException::new);

        System.out.println("when create : " + member.getCreatedDate());
        System.out.println("when update : " + member.getUpdatedDate());
        System.out.println("who create : " + member.getCreatedBy());
        System.out.println("who modify : " + member.getModifiedBy());
    }
}
