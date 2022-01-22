package com.example.projectlivingtogether.entity;

import com.example.projectlivingtogether.dto.MemberDto;
import com.example.projectlivingtogether.repository.CartRepository;
import com.example.projectlivingtogether.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
@Transactional
public class CartTest {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @PersistenceContext
    EntityManager entityManager;

    public Member createMember(){

        MemberDto memberDto = new MemberDto();

        memberDto.setEmail("member@test.mail");
        memberDto.setPassword("member1234@@");
        memberDto.setName("한국인");
        memberDto.setAddress("한국 어딘가");

        return Member.createMember(memberDto, passwordEncoder);
    }

    @Test
    @DisplayName("장바구니, 회원 매핑")
    public void CartAndMemberMappingTest(){

        Member member = createMember();
        memberRepository.save(member);

        Cart cart = new Cart();
        cart.setMember(member);
        cartRepository.save(cart);

        entityManager.flush();
        entityManager.clear();

        Cart savedCart = cartRepository.findById(cart.getId()).orElseThrow(EntityNotFoundException::new);
        Assertions.assertThat(savedCart.getMember().getId()).isEqualTo(member.getId());
    }
}
