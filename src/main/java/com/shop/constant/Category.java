package com.shop.constant;

import java.util.Arrays;

public enum Category {
    SINBAL("sinbal") , PANTY("panty"), MERIYAS("meriyas"), VAJI("vaji") , T("t");

    private final String typeName;

    Category(String typeName){
        this.typeName = typeName;
    }
    public String getTypeName(){
        return this.typeName;
    }

    public static Category find(String input){
        return Arrays.stream( Category.values() )
                .filter( cate -> cate.typeName.equals(input))
                .findAny()
                .orElseThrow( () -> new IllegalArgumentException("잘못된주소 - 상품 메뉴"));
    }

}
