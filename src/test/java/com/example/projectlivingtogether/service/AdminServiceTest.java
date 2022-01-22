package com.example.projectlivingtogether.service;

import com.example.projectlivingtogether.dto.AdminDto;
import com.example.projectlivingtogether.entity.Admin;
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
public class AdminServiceTest {

    @Autowired
    AdminService adminService;

    @Autowired
    PasswordEncoder passwordEncoder;

    public Admin createAdmin(){

        AdminDto adminDto = new AdminDto();

        adminDto.setEmail("admin@test.com");
        adminDto.setPassword("admin1234@@");
        adminDto.setName("테스트 관리자");
        adminDto.setAddress("여기는 대한민국");

        return Admin.createAdmin(adminDto, passwordEncoder);
    }

    @Test
    @DisplayName("관리자 회원가입")
    public void saveAdminTest(){

        Admin admin = createAdmin();
        Admin savedAdmin = adminService.saveAdmin(admin);

        Assertions.assertThat(admin.getRole()).isEqualTo(savedAdmin.getRole());
        Assertions.assertThat(admin.getEmail()).isEqualTo(savedAdmin.getEmail());
        Assertions.assertThat(admin.getPassword()).isEqualTo(savedAdmin.getPassword());
        Assertions.assertThat(admin.getName()).isEqualTo(savedAdmin.getName());
        Assertions.assertThat(admin.getAddress()).isEqualTo(savedAdmin.getAddress());
    }

    @Test
    @DisplayName("관리자 가입여부")
    public void validateMemberTest(){

        Admin admin1 = createAdmin();
        Admin admin2 = createAdmin();

        adminService.saveAdmin(admin1);

        Assertions.assertThatThrownBy(() -> adminService.saveAdmin(admin2))
                .isInstanceOf(IllegalStateException.class);
    }
}
