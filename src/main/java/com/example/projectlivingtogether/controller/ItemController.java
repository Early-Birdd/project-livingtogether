package com.example.projectlivingtogether.controller;

import com.example.projectlivingtogether.dto.ItemDto;
import com.example.projectlivingtogether.dto.SearchDto;
import com.example.projectlivingtogether.entity.Item;
import com.example.projectlivingtogether.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/admin/item/new")
    public String itemRegisterForm(Model model){

        model.addAttribute("itemDto", new ItemDto());

        return "item/itemRegisterForm";
    }

    @PostMapping("/admin/item/new")
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

    @GetMapping("/admin/item/{itemId}")
    public String itemData(@PathVariable("itemId") Long itemId, Model model){

        try{

            ItemDto itemDto = itemService.getItemData(itemId);
            model.addAttribute("itemDto", itemDto);
        }catch (EntityNotFoundException e){

            model.addAttribute("errorMessage", "상품이 존재하지 않습니다.");
            model.addAttribute("itemDto", new ItemDto());

            return "item/itemRegisterForm";
        }

        return "item/itemRegisterForm";
    }

    @PostMapping("/admin/item/{itemId}")
    public String itemUpdate(@Valid ItemDto itemDto, BindingResult bindingResult, @RequestParam("itemImageFile") List<MultipartFile> itemImageFileList, Model model){

        if(bindingResult.hasErrors()){

            return "item/itemRegisterForm";
        }

        if(itemImageFileList.get(0).isEmpty() && itemDto.getId() == null){

            model.addAttribute("errorMessage", "메인 상품 이미지를 필수로 입력하십시오.");

            return "item/itemRegisterForm";
        }

        try{

            itemService.updateItem(itemDto, itemImageFileList);
        }catch (Exception e){

            model.addAttribute("errorMessage", "상품 수정 에러");

            return "item/itemRegisterForm";
        }

        return "redirect:/";
    }

    @GetMapping({"/admin/items", "/admin/items/{page}"})
    public String itemManage(SearchDto searchDto, @PathVariable("page") Optional<Integer> page, Model model){

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 5);

        Page<Item> items = itemService.getItemPage(searchDto, pageable);
        model.addAttribute("items", items);
        model.addAttribute("searchDto", searchDto);
        model.addAttribute("maxPage", 5);

        return "item/itemManageForm";
    }

    @GetMapping("/item/{itemId}")
    public String itemData(Model model, @PathVariable("itemId") Long iemId){

        ItemDto itemDto = itemService.getItemData(iemId);
        model.addAttribute("item", itemDto);

        return "item/itemData";
    }
}
