package com.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter  @Getter
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = " cart_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name="cart_id")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private int count; // 수량

    // 상품을 장바구니에 담겠다 할때 동작할 메서드
    public static CartItem createCartItem(Cart cart , Item item, int count){
        CartItem cartItem = new CartItem();
        cartItem.setCart(cart);
        cartItem.setItem(item);
        cartItem.setCount(count);
        return cartItem;
    }

    public void updateCount(int count) {
        this.count = count;
    }
}
