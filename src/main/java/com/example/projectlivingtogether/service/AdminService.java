package com.example.projectlivingtogether.service;

import com.example.projectlivingtogether.entity.Admin;
import com.example.projectlivingtogether.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService implements UserDetailsService {

    private final AdminRepository adminRepository;

    public Admin saveAdmin(Admin admin) {

        validateAdmin(admin);

        return adminRepository.save(admin);
    }

    private void validateAdmin(Admin admin) {

        Admin findAdmin = adminRepository.findByEmail(admin.getEmail());

        if (findAdmin != null) {

            throw new IllegalStateException("가입 완료된 관리자입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Admin admin = adminRepository.findByEmail(email);

        if(admin == null){

            throw new UsernameNotFoundException(email);
        }

        return org.springframework.security.core.userdetails.User.builder()
                .roles(admin.getRole().toString())
                .username(admin.getEmail())
                .password(admin.getPassword())
                .build();
    }
}
