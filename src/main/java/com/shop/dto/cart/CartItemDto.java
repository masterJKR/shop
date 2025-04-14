package com.shop.dto.cart;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartItemDto {
    private Long itemId;

    @Min( value = 1, message = "최소 1개 이상 담아 주세요")
    private int count;
}
