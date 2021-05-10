package com.example.alwayswin.utils.enums;

public enum ProductStatusCode {
    PENDING("pending", true),  // 审核中
    WAITING("waiting", true),  // 等开始
    BIDDING("bidding", false),  // 竞拍中
    EXTENDED_1("extended1", false),  // 第一次延长
    EXTENDED_2("extended2", false),  // 第二次延长
    EXTENDED_3("extended3", false),  // 第三次延长
    BROUGHT_IN("broughtIn", true),  // 流拍
    SUCCESS("success", false),  // 拍卖成功
    CANCELED("canceled", false);  // 下架/撤回/取消/删除

    private String status;
    private boolean canCancel;   // 在该状态下，该商品是否可以下架/撤回/取消/删除

    public static boolean contains(String target){
        for(ProductStatusCode productStatusCode : ProductStatusCode.values()){
            if(productStatusCode.getStatus().equals(target)){
                return true;
            }
        }
        return false;
    }

    public static boolean isCancelable(String target){
        for(ProductStatusCode productStatusCode : ProductStatusCode.values()){
            if(productStatusCode.getStatus().equals(target)){
                return productStatusCode.canCancel;
            }
        }
        return false;
    }

    public static boolean isBidding(String target) {
        return target.equals(BIDDING.status) || isExtending(target);
    }

    public static boolean isExtending(String target) {
        for(ProductStatusCode productStatusCode : ProductStatusCode.values()){
            if(productStatusCode.getStatus().equals(target)
                    && productStatusCode.getStatus().contains("extended")) {
                return true;
            }
        }
        return false;
    }

    ProductStatusCode(String status, boolean canCancel) {
        this.status = status;
        this.canCancel = canCancel;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isCanCancel() {
        return canCancel;
    }

    public void setCanCancel(boolean canCancel) {
        this.canCancel = canCancel;
    }

    @Override
    public String toString() {
        return "ProductStatusCode{" +
                "status=" + status +
                ", canCancel='" + canCancel + '\'' +
                '}';
    }
}
