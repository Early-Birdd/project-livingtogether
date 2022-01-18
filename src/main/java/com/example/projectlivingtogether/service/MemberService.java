package com.example.projectlivingtogether.service;

import com.example.projectlivingtogether.entity.Member;
import com.example.projectlivingtogether.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    public Member saveMember(Member member) {

        validateMember(member);

        return memberRepository.save(member);
    }

    private void validateMember(Member member) {

        Member findMember = memberRepository.findByEmail(member.getEmail());

        if (findMember != null) {

            throw new IllegalStateException("가입 완료된 회원입니다.");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(email);

        if(member == null){

            throw new UsernameNotFoundException(email);
        }

        return User.builder()
                .roles(member.getRole().toString())
                .username(member.getEmail())
                .password(member.getPassword())
                .build();
    }
}
