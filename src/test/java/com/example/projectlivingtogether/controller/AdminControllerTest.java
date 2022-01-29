package com.example.projectlivingtogether.controller;

import com.example.projectlivingtogether.dto.AdminDto;
import com.example.projectlivingtogether.entity.Admin;
import com.example.projectlivingtogether.service.AdminService;
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
public class AdminControllerTest {

    @Autowired
    private AdminService adminService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private MockMvc mockMvc;

    public Admin createAdmin(String email, String password){

        AdminDto adminDto = new AdminDto();
        adminDto.setEmail(email);
        adminDto.setPassword(password);
        adminDto.setName("한국인");
        adminDto.setAddress("한국 어딘가");

        Admin admin = Admin.createAdmin(adminDto, passwordEncoder);

        return adminService.saveAdmin(admin);
    }

    @Test
    @DisplayName("관리자 로그인")
    public void AdminLoginTest() throws Exception{

        String email = "admin@test.mail";
        String password = "admin1234@@";

        this.createAdmin(email, password);

        mockMvc.perform(formLogin().userParameter("email")
                .loginProcessingUrl("/all/login")
                .user(email)
                .password(password))
                .andExpect(SecurityMockMvcResultMatchers.authenticated());
    }

    @Test
    @DisplayName("관리자 로그인 실패")
    public void AdminLoginFailTest() throws Exception{

        String email = "admin@test.mail";
        String password = "admin1234@@";

        this.createAdmin(email, password);

        mockMvc.perform(formLogin().userParameter("email")
                .loginProcessingUrl("/all/login")
                .user(email)
                .password("admin12345@@"))
                .andExpect(SecurityMockMvcResultMatchers.unauthenticated());
    }
}
