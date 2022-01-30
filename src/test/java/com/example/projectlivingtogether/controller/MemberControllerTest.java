package com.example.projectlivingtogether.controller;

import com.example.projectlivingtogether.dto.MemberDto;
import com.example.projectlivingtogether.entity.Member;
import com.example.projectlivingtogether.enumclass.Role;
import com.example.projectlivingtogether.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
@Transactional
@AutoConfigureMockMvc
public class MemberControllerTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    public Member createMember(String email, String password){

        MemberDto memberDto = new MemberDto();
        memberDto.setRole(Role.USER);
        memberDto.setEmail(email);
        memberDto.setPassword(password);
        memberDto.setName("한국인");
        memberDto.setAddress("한국 어딘가");

        Member member = Member.createMember(memberDto, passwordEncoder);

        return memberService.saveMember(member);
    }

    @Test
    @DisplayName("회원 로그인")
    public void UserLoginTest() throws Exception{

        String email = "user@test.mail";
        String password = "user1234@@";

        this.createMember(email, password);

        mockMvc.perform(formLogin().userParameter("email")
                .loginProcessingUrl("/all/login")
                .user(email)
                .password(password))
                .andExpect(SecurityMockMvcResultMatchers.authenticated());
    }

    @Test
    @DisplayName("회원 로그인 실패")
    public void MemberLoginFailTest() throws Exception{

        String email = "member@test.mail";
        String password = "member1234@@";

        this.createMember(email, password);

        mockMvc.perform(formLogin().userParameter("email")
                .loginProcessingUrl("/all/login")
                .user(email)
                .password("member12345@@"))
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated());
    }
}
