package com.example.projectlivingtogether.service;

import com.example.projectlivingtogether.dto.MemberDto;
import com.example.projectlivingtogether.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
@Transactional
public class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Member createMember(){

        MemberDto memberDto = new MemberDto();

        memberDto.setEmail("member@test.com");
        memberDto.setPassword("member1234@@");
        memberDto.setName("테스트인");
        memberDto.setAddress("여기는 대한민국");

        return Member.createMember(memberDto, passwordEncoder);
    }

    @Test
    @DisplayName("고객 회원가입")
    public void saveMemberTest(){

        Member member = createMember();
        Member savedMember = memberService.saveMember(member);

        Assertions.assertThat(member.getRole()).isEqualTo(savedMember.getRole());
        Assertions.assertThat(member.getEmail()).isEqualTo(savedMember.getEmail());
        Assertions.assertThat(member.getPassword()).isEqualTo(savedMember.getPassword());
        Assertions.assertThat(member.getName()).isEqualTo(savedMember.getName());
        Assertions.assertThat(member.getAddress()).isEqualTo(savedMember.getAddress());
    }

    @Test
    @DisplayName("고객 가입여부")
    public void validateMemberTest(){

        Member member1 = createMember();
        Member member2 = createMember();

        memberService.saveMember(member1);

        Assertions.assertThatThrownBy(() -> memberService.saveMember(member2))
                .isInstanceOf(IllegalStateException.class);
    }
}
