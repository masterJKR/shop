package com.shop.constant;

public enum Category {
    SINBAL("sinbal") , PANTY("panty"), MERIYAS("meriyas"), VAJI("vaji") , T("t");

    private final String typeName;

    Category(String typeName){
        this.typeName = typeName;
    }
    public String getTypeName(){
        return this.typeName;
    }

}
