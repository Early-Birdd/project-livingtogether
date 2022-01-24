package com.example.projectlivingtogether.dto;

import com.example.projectlivingtogether.ItemStatus;
import com.example.projectlivingtogether.entity.Item;
import lombok.Data;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class ItemDto {

    private Long id;

    private ItemStatus itemStatus;

    @NotNull(message = "상품 가격을 필수로 입력하십시오.")
    private Integer price;

    @NotNull(message = "상품 재고 수량을 필수로 입력하십시오.")
    private Integer itemQuantity;

    @NotBlank(message = "상품명을 필수로 입력하십시오.")
    private String itemName;

    @NotBlank(message = "상품 설명을 필수로 입력하십시오.")
    private String itemDetail;

    private List<ItemImageDto> itemImageDtoList = new ArrayList<>();
    private List<Long> itemImageIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Item createItem(){

        return modelMapper.map(this, Item.class);
    }

    public static ItemDto of(Item item){

        return modelMapper.map(item, ItemDto.class);
    }
}
