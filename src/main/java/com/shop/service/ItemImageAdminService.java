package com.shop.service;


import com.shop.entity.ItemImage;
import com.shop.repository.ItemImageRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.thymeleaf.util.StringUtils;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ItemImageAdminService {
    private final ItemImageRepo itemImageRepo;
    private final FileService fileService;

    // 이미지 업로드와  테이블 저장하기 위한 메서드 - 이미지 1개씩
    public void saveImg(ItemImage itemImage, MultipartFile multipartFile) throws Exception {

        String originalName = multipartFile.getOriginalFilename();
        String imgName ="";
        String imgUrl="";

        // 이미지 업로드를위해 FileService의 메서드 호출
        if( !StringUtils.isEmpty(originalName) ){
            imgName = fileService.uploadFile(originalName,multipartFile.getBytes());
            imgUrl = "/itemImg/"+imgName;
        }
        itemImage.setOriginalName( originalName );
        itemImage.setImgUrl( imgUrl );
        itemImage.setImgName( imgName );
        // 테이블에저장
        itemImageRepo.save(itemImage);
    }
}
