package com.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class ItemImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "item_img_id")
    private Long id;

    private String imgName; // 이미지 이름
    private String originalName; //원본 이름
    private String imgUrl; // 이미지 경로
    private String repImgYn ; // 대표 이미지 설정- 썸네일용

    @ManyToOne
    @JoinColumn( name = "item_id")
    private Item item;
}
