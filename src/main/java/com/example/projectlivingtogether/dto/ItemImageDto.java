package com.example.projectlivingtogether.dto;

import com.example.projectlivingtogether.entity.ItemImage;
import lombok.Data;
import org.modelmapper.ModelMapper;

@Data
public class ItemImageDto {

    private Long id;

    private String imageName;
    private String originImageName;
    private String imageUrl;
    private String rpImage;

    private static ModelMapper modelMapper = new ModelMapper();

    public static ItemImageDto of(ItemImage itemImage){

        return modelMapper.map(itemImage, ItemImageDto.class);
    }
}
