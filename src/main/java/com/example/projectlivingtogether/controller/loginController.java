package com.example.projectlivingtogether.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/all")
public class loginController {

    @GetMapping("/login")
    public String login(){

        return "/loginForm";
    }

    @GetMapping("/login/error")
    public String loginError(Model model){

        model.addAttribute("loginErrorMessage", "아이디 또는 비밀번호를 확인하십시오.");

        return "/loginForm";
    }
}
