package com.shop.dto.item;

import com.shop.constant.ItemSellStatus;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ItemSearchDto {  // 상품 검색 할때 사용할 거!!!

    //찬찬히 제발... 잘 살피면서.... 작성해요... 급하게... 치지말고....
    // (내가 다했죠?  해도 천천히....)  알았나??  목소리가 작은데?  대답안한사람은?
    private String searchDateType;
    private ItemSellStatus itemSellStatus;
    private String searchBy;
    private String searchQuery="";
}
