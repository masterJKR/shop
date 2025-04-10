package com.shop.dto.item;

import com.shop.entity.ItemImage;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Setter@Getter
public class ItemImgDto {
    private Long id;
    private String imgName;
    private String originalName;
    private String imgUrl;
    private String repImgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    public static ItemImgDto from(ItemImage itemImage){
        return modelMapper.map(itemImage, ItemImgDto.class);
    }
}
