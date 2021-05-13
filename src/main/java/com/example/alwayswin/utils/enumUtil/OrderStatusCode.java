package com.example.alwayswin.utils.enumUtil;

public enum OrderStatusCode {
    PLACED("placed", false),
    PAID("paid", false),
    SHIPPED("shipped", false),
    RECEIVED("received", true);

    private String status;
    private boolean canDelete;   // under this status, can this order be deleted

    public static boolean contains(String target){
        for(OrderStatusCode orderStatusCode : OrderStatusCode.values()){
            if(orderStatusCode.getStatus().equals(target)){
                return true;
            }
        }
        return false;
    }

    public static boolean isDeletable(String target){
        for(OrderStatusCode orderStatusCode : OrderStatusCode.values()){
            if(orderStatusCode.getStatus().equals(target)){
                return orderStatusCode.canDelete;
            }
        }
        return false;
    }

    OrderStatusCode(String status, boolean canDelete) {
        this.status = status;
        this.canDelete = canDelete;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    @Override
    public String toString() {
        return "OrderStatusCode{" +
                "status=" + status +
                ", canDelete='" + canDelete + '\'' +
                '}';
    }
}
