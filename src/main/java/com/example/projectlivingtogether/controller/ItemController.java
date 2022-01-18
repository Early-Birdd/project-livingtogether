package com.example.projectlivingtogether.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ItemController {

    @GetMapping("/admin/item/new")
    public String itemRegisterForm(){

        return "/item/itemRegisterForm";
    }
}
