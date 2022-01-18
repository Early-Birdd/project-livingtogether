package com.example.projectlivingtogether.controller;

import com.example.projectlivingtogether.dto.MemberDto;
import com.example.projectlivingtogether.entity.Member;
import com.example.projectlivingtogether.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public String memberRegisterForm(Model model){

        model.addAttribute("memberDto", new MemberDto());

        return "member/memberRegisterForm";
    }

    @PostMapping("/signup")
    public String newMember(@Valid MemberDto memberDto, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){

            return "member/memberRegisterForm";
        }

        try{

            Member member = Member.createMember(memberDto, passwordEncoder);
            memberService.saveMember(member);
        }catch (IllegalStateException e){

            model.addAttribute("errorMessage", e.getMessage());

            return "member/memberRegisterForm";
        }

        return "redirect:/";
    }

    @GetMapping("/login")
    public String login(){

        return "/member/loginForm";
    }

    @GetMapping("/login/error")
    public String loginError(Model model){

        model.addAttribute("loginErrorMessage", "아이디 또는 비밀번호를 확인하십시오.");

        return "/member/loginForm";
    }
}
