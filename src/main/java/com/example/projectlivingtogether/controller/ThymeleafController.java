package com.example.projectlivingtogether.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class ThymeleafController {

    @GetMapping("/main")
    public String thymeleafMain(){

        return "template/layout/layoutMain";
    }
}
