package com.example.alwayswin.utils.enums;

public enum ProductStatusCode {
    PENDING("pending", true),
    WAITING("waiting", true),
    BIDDING("bidding", false),
    EXTENDED("extended", false),
    BROUGHT_IN("broughtIn", true),
    SUCCESS("success", false),
    CANCELED("canceled", false);

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
