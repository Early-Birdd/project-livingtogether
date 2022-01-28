package com.example.projectlivingtogether.entity;

import com.example.projectlivingtogether.enumclass.Role;
import com.example.projectlivingtogether.dto.AdminDto;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Data
@Entity
@Table(name = "admin")
public class Admin extends BaseEntity{

    @Id
    @Column(name = "admin_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(unique = true)
    private String email;

    private String password;
    private String name;
    private String address;

    public static Admin createAdmin(AdminDto adminDto, PasswordEncoder passwordEncoder){

        Admin admin = new Admin();
        admin.setRole(Role.ADMIN);
        admin.setEmail(adminDto.getEmail());
        String password = passwordEncoder.encode(adminDto.getPassword());
        admin.setPassword(password);
        admin.setName(adminDto.getName());
        admin.setAddress(adminDto.getAddress());

        return admin;
    }
}
