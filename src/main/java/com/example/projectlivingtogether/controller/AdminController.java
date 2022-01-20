package com.example.projectlivingtogether.controller;

import com.example.projectlivingtogether.dto.AdminDto;
import com.example.projectlivingtogether.entity.Admin;
import com.example.projectlivingtogether.service.AdminService;
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
@RequestMapping("/admins")
public class AdminController {

    private final AdminService adminService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/signup")
    public String adminRegisterForm(Model model){

        model.addAttribute("adminDto", new AdminDto());

        return "admin/adminRegisterForm";
    }

    @PostMapping("/signup")
    public String newAdmin(@Valid AdminDto adminDto, BindingResult bindingResult, Model model){

        if(bindingResult.hasErrors()){

            return "admin/adminRegisterForm";
        }

        try{

            Admin admin = Admin.createAdmin(adminDto, passwordEncoder);
            adminService.saveAdmin(admin);
        }catch (IllegalStateException e){

            model.addAttribute("errorMessage", e.getMessage());

            return "admin/adminRegisterForm";
        }

        return "redirect:/";
    }
}
