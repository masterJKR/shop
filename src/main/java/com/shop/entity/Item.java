package com.shop.entity;

import com.shop.constant.Category;
import com.shop.constant.ItemSellStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;

@Entity
@Setter@Getter
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_id")
    private Long id;

    @Column(nullable = false, length = 100)
    private String itemName; // 상품 이름

    @Column(nullable = false)
    private int price; //상품 가격

    @Column(nullable = false)
    private int stockNumber; // 상품 수량

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String itemDetail;// 상품 상세 내용

    @Enumerated(EnumType.STRING)
    private ItemSellStatus itemSellStatus; // 상품 판매 가능 상태

    @Enumerated(EnumType.STRING)
    private Category category; // 상품 종류

    public void removeStock(int count) {
    }

    public void addStock(int count) {
    }
}



