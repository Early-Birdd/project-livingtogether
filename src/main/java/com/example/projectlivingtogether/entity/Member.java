package com.example.projectlivingtogether.entity;

import com.example.projectlivingtogether.enumclass.Role;
import com.example.projectlivingtogether.dto.MemberDto;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Data
@Entity
@Table(name = "member")
public class Member extends BaseEntity {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(unique = true)
    private String email;

    private String password;
    private String name;
    private String address;

    public static Member createMember(MemberDto memberDto, PasswordEncoder passwordEncoder){

        Member member = new Member();
        member.setRole(memberDto.getRole());
        member.setEmail(memberDto.getEmail());
        String password = passwordEncoder.encode(memberDto.getPassword());
        member.setPassword(password);
        member.setName(memberDto.getName());
        member.setAddress(memberDto.getAddress());

        return member;
    }
}
