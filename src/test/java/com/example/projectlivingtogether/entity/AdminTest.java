package com.example.projectlivingtogether.entity;

import com.example.projectlivingtogether.repository.AdminRepository;
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
public class AdminTest {

    @Autowired
    AdminRepository adminRepository;

    @PersistenceContext
    EntityManager entityManager;

    @Test
    @DisplayName("Admin Auditing")
    @WithMockUser(username = "Admin Korean", roles = "ADMIN")
    public void adminAuditingTest(){

        Admin testAdmin = new Admin();
        adminRepository.save(testAdmin);

        entityManager.flush();
        entityManager.clear();

        Admin admin = adminRepository.findById(testAdmin.getId()).orElseThrow(EntityNotFoundException::new);

        System.out.println("when create : " + admin.getCreatedDate());
        System.out.println("when update : " + admin.getUpdatedDate());
        System.out.println("who create : " + admin.getCreatedBy());
        System.out.println("who modify : " + admin.getModifiedBy());
    }
}
