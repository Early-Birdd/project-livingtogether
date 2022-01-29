package com.example.projectlivingtogether.dto;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class CartItemDto {

    @NotNull(message = "상품 아이디를 필수로 입력하십시오.")
    private Long itemId;

    @Min(value = 1, message = "상품을 1개 이상 담아주십시오.")
    private int count;
}
