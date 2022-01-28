package com.example.projectlivingtogether.controller;

import com.example.projectlivingtogether.dto.MainItemDto;
import com.example.projectlivingtogether.dto.SearchDto;
import com.example.projectlivingtogether.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ItemService itemService;

    @GetMapping("/")
    public String main(SearchDto searchDto, Optional<Integer> page, Model model){

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);

        Page<MainItemDto> items = itemService.getMainItemPage(searchDto, pageable);
        model.addAttribute("items", items);
        model.addAttribute("searchDto", searchDto);
        model.addAttribute("maxPage", 5);

        return "main";
    }
}
