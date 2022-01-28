package com.example.projectlivingtogether.service;

import com.example.projectlivingtogether.dto.ItemDto;
import com.example.projectlivingtogether.dto.ItemImageDto;
import com.example.projectlivingtogether.dto.MainItemDto;
import com.example.projectlivingtogether.dto.SearchDto;
import com.example.projectlivingtogether.entity.Item;
import com.example.projectlivingtogether.entity.ItemImage;
import com.example.projectlivingtogether.repository.ItemImageRepository;
import com.example.projectlivingtogether.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {

    private final ItemImageService itemImageService;
    private final ItemRepository itemRepository;
    private final ItemImageRepository itemImageRepository;

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

    @Transactional(readOnly = true)
    public ItemDto getItemData(Long itemId){

        List<ItemImage> itemImageList = itemImageRepository.findByItemIdOrderByIdAsc(itemId);
        List<ItemImageDto> itemImageDtoList = new ArrayList<>();

        for(ItemImage itemImage : itemImageList){

            ItemImageDto itemImageDto = ItemImageDto.of(itemImage);
            itemImageDtoList.add(itemImageDto);
        }

        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);
        ItemDto itemDto = ItemDto.of(item);
        itemDto.setItemImageDtoList(itemImageDtoList);

        return itemDto;
    }

    public Long updateItem(ItemDto itemDto, List<MultipartFile> itemImageFileList) throws Exception{

        Item item = itemRepository.findById(itemDto.getId()).orElseThrow(EntityNotFoundException::new);
        item.updateItem(itemDto);

        List<Long> itemImageIds = itemDto.getItemImageIds();

        for(int i = 0; i < itemImageFileList.size(); i++){

            itemImageService.updateItemImage(itemImageIds.get(i), itemImageFileList.get(i));
        }

        return item.getId();
    }

    @Transactional(readOnly = true)
    public Page<Item> getItemPage(SearchDto searchDto, Pageable pageable){

        return itemRepository.getItemPage(searchDto, pageable);
    }

    @Transactional(readOnly = true)
    public Page<MainItemDto> getMainItemPage(SearchDto searchDto, Pageable pageable){

        return itemRepository.getMainItemPage(searchDto, pageable);
    }
}
