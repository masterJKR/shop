package com.shop.dto.item;


import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import lombok.Getter;
import lombok.Setter;

@Getter  @Setter
public class ItemListDto { // 상품 목록출력용

    private Long id;
    private String itemName;
    private Integer price;
    private ItemSellStatus itemSellStatus;

    private String imgUrl; // 썸네일 이미지

    public static ItemListDto of(Item item, String imgUrl){
        ItemListDto itemListDto = new ItemListDto();

        itemListDto.setId( item.getId());
        itemListDto.setItemName(item.getItemName());
        itemListDto.setPrice( item.getPrice());
        itemListDto.setItemSellStatus( item.getItemSellStatus());
        itemListDto.setImgUrl( imgUrl );

        return itemListDto;
    }
}
