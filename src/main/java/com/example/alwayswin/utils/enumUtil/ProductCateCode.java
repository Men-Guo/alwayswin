package com.example.alwayswin.utils.enumUtil;

public enum ProductCateCode {

    CAMERA(1, "camera"),
    CELL_PHONE(1, "cell phone"),
    ACCESSORY(1, "accessory"),
    COMPUTER(1, "computer"),
    TABLET(1, "tablet"),
    NETWORK_HARDWARE(1, "network hardware"),
    TV(1, "tv"),
    SMART_HOME (1, "smart home"),
    PORTABLE_AUDIO(1, "portable audio"),
    CAR_ELECTRONICS(1, "car electronics"),
    GAME_CONSOLE(1, "gaming console"),
    VR(1, "vr"),
    OTHERS(1, "others");

    private int level;
    private String cate;

    ProductCateCode(int level, String cate) {
        this.level = level;
        this.cate = cate;
    }

    public static boolean contains(String target){
        for(ProductCateCode productCateCode : ProductCateCode.values()){
            if(productCateCode.getCate().equals(target)){
                return true;
            }
        }
        return false;
    }

    public int getLevel() {
        return level;
    }

    public String getCate() {
        return cate;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void setCate(String cate) {
        this.cate = cate;
    }

    @Override
    public String toString() {
        return "ProductCateCode{" +
                "level=" + level +
                ", cate='" + cate + '\'' +
                '}';
    }
}
