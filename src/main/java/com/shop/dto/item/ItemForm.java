package com.shop.dto.item;

import com.shop.constant.Category;
import com.shop.constant.ItemSellStatus;
import com.shop.entity.Item;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter @Setter
public class ItemForm {
    private Long id; // 상품 프라이머리키 id

    @NotEmpty(message = "상품명은 필수 야")
    private String itemName;

    @NotNull(message = "상품 가격 넣으라고!! 이은진!!")
    private Integer price;

    @NotNull(message = "상품 재고 수량 입력해야되!! 팔아야 돈 벌지")
    private Integer stockNumber;

    @NotEmpty(message = "상품 설명 입력 해야되요~~")
    private String itemDetail;

    private ItemSellStatus itemSellStatus;

    private Category category;

    private static ModelMapper modelMapper = new ModelMapper();

    // entity->dto
    public static ItemForm from(Item item){
        return modelMapper.map(item, ItemForm.class);
    }

    //dto -> entity
    public Item createItem(){
        return modelMapper.map(this, Item.class);
    }
}
