package com.example.projectlivingtogether.service;

import com.example.projectlivingtogether.entity.ItemImage;
import com.example.projectlivingtogether.repository.ItemImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemImageService {

    @Value("${itemImageLocation}")
    private String itemImageLocation;

    private final FileService fileService;
    private final ItemImageRepository itemImageRepository;

    public void saveItemImage(ItemImage itemImage, MultipartFile itemImageFile) throws Exception{

        String imageName = "";
        String originImageName = itemImageFile.getOriginalFilename();
        String imageUrl = "";

        if(!StringUtils.isEmpty(originImageName)){

            imageName = fileService.uploadFile(itemImageLocation, originImageName, itemImageFile.getBytes());
            imageUrl = "/images/item/" + imageName;
        }

        itemImage.updateImage(imageName, originImageName, imageUrl);
        itemImageRepository.save(itemImage);
    }

    public void updateItemImage(Long itemImageId, MultipartFile itemImageFile) throws Exception{

        if(!itemImageFile.isEmpty()){

            ItemImage savedItemImage = itemImageRepository.findById(itemImageId).orElseThrow(EntityNotFoundException::new);

            if(!StringUtils.isEmpty(savedItemImage.getImageName())){

                fileService.deleteFile(itemImageLocation + "/" + savedItemImage.getImageName());
            }

            String originImageName = itemImageFile.getOriginalFilename();
            String imageName = fileService.uploadFile(itemImageLocation, originImageName, itemImageFile.getBytes());
            String imageUrl = "/images/item/" + imageName;

            savedItemImage.updateImage(imageName, originImageName, imageUrl);
        }
    }
}
