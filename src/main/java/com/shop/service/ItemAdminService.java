package com.shop.service;

import com.shop.dto.item.ItemForm;
import com.shop.entity.Item;
import com.shop.entity.ItemImage;
import com.shop.repository.ItemImageRepo;
import com.shop.repository.ItemRepo;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemAdminService {
    private final ItemRepo itemRepo;
    private final ItemImageRepo itemImageRepo;
    private final ItemImageAdminService itemImageAdminService;

    // 상품 내용과 이미지를 저장하기 위한 service 메서드이다.
    // 여기서 레포지토리의 메서드를 호출하여 테이블에 저장할것이다.
    // dto를 entity로 넘겨주기를 해서 레포지토리 메서드 호출한다.
    // 이미지는 업로드 를 하고 entity에 이미지관련 저장하기
    public void saveItem( ItemForm itemForm, List<MultipartFile> multipartFileList) throws Exception {
        Item item = itemForm.createItem(); // dto -> entity ( 상품 내용만 )
        itemRepo.save(item);  // 상품 내용 먼저 테이블에 저장 해야한다.
        //item 테이블에 저장하고  item_id 컬럼의 값이 필요하다.
        // 이미지를 테이블에 저장하려면 어떤 상품의 이미지인가 를 표시해야되기때문에 item테이블에
        // 먼저 저장한다.
        // save 메서드를 실행하면 해당 엔티티 객체에  primary key 컬럼의 값이 저장된다.
        // 추가로  primary key 컬럼의 값을 가져오기 위한 작업을 할 필요 없다.

        //이미지 업로드 와 테이블에 저장하기
        for(int i=0; i<multipartFileList.size(); i++){
            ItemImage itemImage = new ItemImage();
            itemImage.setItem(item); // 이미지를 테이블에 저장하기전에 item_id 가지도록 하기위해
            if( i == 0 )
                itemImage.setRepImgYn("Y"); // 첫번째 이미지가 대표이미지 - 섬네일용
            else
                itemImage.setRepImgYn("N");

            //이미지 service의 메서드를 호출하여 업로드및 테이블 저장
            itemImageAdminService.saveImg(itemImage, multipartFileList.get(i) );
        }

    }
}
