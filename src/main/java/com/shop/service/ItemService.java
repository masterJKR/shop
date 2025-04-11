package com.shop.service;

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
}
