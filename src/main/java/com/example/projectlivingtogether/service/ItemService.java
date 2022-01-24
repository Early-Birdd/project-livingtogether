package com.example.projectlivingtogether.service;

import com.example.projectlivingtogether.dto.ItemDto;
import com.example.projectlivingtogether.entity.Item;
import com.example.projectlivingtogether.entity.ItemImage;
import com.example.projectlivingtogether.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemImageService itemImageService;
    private final ItemRepository itemRepository;

    public Long saveItem(ItemDto itemDto, List<MultipartFile> itemImageFileList) throws Exception{

        Item item = itemDto.createItem();
        itemRepository.save(item);

        for(int i = 0; i < itemImageFileList.size(); i++){

            ItemImage itemImage = new ItemImage();
            itemImage.setItem(item);

            if(i == 0){

                itemImage.setRpImage("Y");
            }else {

                itemImage.setRpImage("N");
            }

            itemImageService.saveItemImage(itemImage, itemImageFileList.get(i));
        }

        return  item.getId();
    }
}
