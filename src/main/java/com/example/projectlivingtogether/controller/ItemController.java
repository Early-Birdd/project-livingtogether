package com.example.projectlivingtogether.controller;

import com.example.projectlivingtogether.dto.ItemDto;
import com.example.projectlivingtogether.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/admin/item/new")
    public String itemRegisterForm(Model model){

        model.addAttribute("itemDto", new ItemDto());

        return "item/itemRegisterForm";
    }

    @PostMapping(value = "/admin/item/new")
    public String newItem(@Valid ItemDto itemDto, BindingResult bindingResult, Model model, @RequestParam("itemImageFile") List<MultipartFile> itemImageFileList){

        if(bindingResult.hasErrors()){

            return "item/itemRegisterForm";
        }

        if(itemImageFileList.get(0).isEmpty() && itemDto.getId() == null){

            model.addAttribute("errorMessage", "메인 상품 이미지를 필수로 입력하십시오.");

            return "item/itemRegisterForm";
        }

        try{

            itemService.saveItem(itemDto, itemImageFileList);
        }catch (Exception e){

            model.addAttribute("errorMessage", "상품 등록 에러");

            return "item/itemRegisterForm";
        }

        return "redirect:/";
    }
}
