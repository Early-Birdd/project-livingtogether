package com.example.projectlivingtogether.service;

import com.example.projectlivingtogether.ItemStatus;
import com.example.projectlivingtogether.dto.ItemDto;
import com.example.projectlivingtogether.entity.Item;
import com.example.projectlivingtogether.entity.ItemImage;
import com.example.projectlivingtogether.repository.ItemImageRepository;
import com.example.projectlivingtogether.repository.ItemRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.yml")
@Transactional
public class ItemServiceTest {

    @Autowired
    ItemService itemService;

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    ItemImageRepository itemImageRepository;

    List<MultipartFile> createItemImageFiles() throws Exception{

        List<MultipartFile> itemImageFileList = new ArrayList<>();

        for(int i = 0; i < 5; i++){

            String path = "C:/livingtogether/item/";
            String imageName = "image" + i + ".jpg";

            MockMultipartFile mockMultipartFile = new MockMultipartFile(path, imageName, "image/jpg", new byte[]{1, 2, 3, 4});
            itemImageFileList.add(mockMultipartFile);
        }

        return itemImageFileList;
    }

    @Test
    @DisplayName("상품 등록")
    @WithMockUser(username = "Admin Korean", roles = "ADMIN")
    public void saveItem() throws Exception{

        ItemDto itemDto = new ItemDto();

        itemDto.setItemStatus(ItemStatus.SALE);
        itemDto.setPrice(50000);
        itemDto.setItemQuantity(50);
        itemDto.setItemName("테스트용 상품");
        itemDto.setItemDetail("테스트용 설명입니다.");

        List<MultipartFile> itemImageFileList = createItemImageFiles();
        Long itemId = itemService.saveItem(itemDto, itemImageFileList);

        List<ItemImage> itemImageList = itemImageRepository.findByItemIdOrderByIdAsc(itemId);

        Item item = itemRepository.findById(itemId).orElseThrow(EntityNotFoundException::new);

        Assertions.assertThat(itemDto.getItemStatus()).isEqualTo(item.getItemStatus());
        Assertions.assertThat(itemDto.getPrice()).isEqualTo(item.getPrice());
        Assertions.assertThat(itemDto.getItemQuantity()).isEqualTo(item.getItemQuantity());
        Assertions.assertThat(itemDto.getItemName()).isEqualTo(item.getItemName());
        Assertions.assertThat(itemDto.getItemDetail()).isEqualTo(item.getItemDetail());

        Assertions.assertThat(itemImageFileList.get(0).getOriginalFilename()).isEqualTo(itemImageList.get(0).getOriginImageName());
    }
}
