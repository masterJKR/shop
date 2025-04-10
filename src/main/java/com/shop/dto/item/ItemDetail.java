package com.shop.dto.item;


import com.shop.entity.Item;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter  @Setter
public class ItemDetail {
    private Long id; // 상품 프라이머리키 id
    private String itemName;
    private Integer price;
    private Integer stockNumber;
    private String itemDetail;

    private List<ItemImgDto> itemImgDtoList;

    // 상품 상세 보기 페이지 출력용    entity - > dto
    public static ItemDetail of(Item item, List<ItemImgDto> itemImgDtoList){
        ItemDetail itemDetail = new ItemDetail();

        itemDetail.setId( item.getId());
        itemDetail.setItemDetail(item.getItemDetail());
        itemDetail.setItemName( item.getItemName());
        itemDetail.setPrice( item.getPrice());
        itemDetail.setStockNumber(item.getStockNumber());
        itemDetail.setItemImgDtoList( itemImgDtoList );

        return itemDetail;
    }

}
