package com.company.ordersbackend.domain;


public enum OrderStatus {
    NEW,
    REALISE,
    FINISHED;

    OrderStatus() {
    }

    public static OrderStatus getByString(String statusString){
        for (OrderStatus value : OrderStatus.values()) {
            if(value.toString().equals(statusString.toUpperCase()   ))
                return value;
        }
        return null;
    }
}
