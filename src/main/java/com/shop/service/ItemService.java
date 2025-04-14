package com.shop.service;

import com.shop.constant.Category;
import com.shop.dto.item.ItemDetail;
import com.shop.dto.item.ItemImgDto;
import com.shop.dto.item.ItemListDto;
import com.shop.entity.Item;
import com.shop.entity.ItemImage;
import com.shop.repository.ItemImageRepo;
import com.shop.repository.ItemRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepo itemRepo;
    private final ItemImageRepo itemImageRepo;

    public ItemDetail getItem(Long itemId) {
        // 상세하게 보려는 상품 의 id로 조회
        Item item = itemRepo.findById(itemId).orElse(null);
        // 해당 상품의 id를 가진 이미지들을 조회
        List<ItemImage> itemImages = itemImageRepo.findByItemId(itemId);

        // 이미지들이 entity에 담겨있으니까  dto로 변환
        List<ItemImgDto> itemImgDtoList = new ArrayList<>();
        for( ItemImage itemImage : itemImages){
            itemImgDtoList.add( ItemImgDto.from(itemImage) );
        }
        ItemDetail itemDetail = ItemDetail.of(item, itemImgDtoList);
        return itemDetail;
    }


    //  상품 메뉴 클릭시  상품별 목록 가져와서 보내기 ( Db에서 가져와 entity에 담고  서비스에서 받아서
    //  DTo로 넘기고  Dto를 컨트롤에 넘기기)

    public List<ItemListDto> getItemList(String category) {
        Category menu = getEnum(category);

        List<ItemListDto> itemListDtos = new ArrayList<>();  // 컨트롤에 전달할 리스트

        List<Item> itemList = itemRepo.findAllByCategory(menu);

        // 각 상품 별 대표 이미지를 테이블에서 가져오기 위해 반복문 돌리기
        for( Item item : itemList){
            ItemImage itemImage = itemImageRepo.findByItemIdAndRepImgYn(item.getId(), "Y");

            itemListDtos.add(  ItemListDto.of( item , itemImage.getImgUrl()) );
        }

        return itemListDtos;
    }

    //  열거형 카테고리 찾기
    private Category getEnum(String category){
        switch(category){
            case "sinbal":
                return Category.SINBAL;
            case "panty":
                return Category.PANTY;
            case "meriyas":
                return Category.MERIYAS;
            case "vaji":
                return Category.VAJI;
            case "t":
                return Category.T;
            default:
                throw new RuntimeException("잘못된 주소입니다. - 상품메뉴");
        }

    }
}
