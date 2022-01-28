package com.example.projectlivingtogether.repository;

import com.example.projectlivingtogether.dto.MainItemDto;
import com.example.projectlivingtogether.dto.SearchDto;
import com.example.projectlivingtogether.entity.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ItemRepositoryCustom {

    Page<Item> getItemPage(SearchDto searchDto, Pageable pageable);
    Page<MainItemDto> getMainItemPage(SearchDto searchDto, Pageable pageable);
}
