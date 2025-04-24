package com.shop.dto.cart;

import com.shop.entity.Cart;
import com.shop.entity.CartItem;
import com.shop.entity.Item;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CartListDto {
    private Long cartItemId;
    private String itemName;
    private String imgUrl;
    private int itemPrice;
    private int count;

    public static CartListDto fromCart(CartItem cartItem, String imgUrl) {
        CartListDto dto = new CartListDto();
        dto.setCartItemId(cartItem.getId());
        dto.setItemName(cartItem.getItem().getItemName());
        dto.setImgUrl(imgUrl);
        dto.setItemPrice(cartItem.getItem().getPrice());
        dto.setCount(cartItem.getCount());
        return dto;
    }
}
