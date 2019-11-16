package com.mtsealove.github.food_delivery_manager.Entity;

public class Sales {
    String OrderDate;
    int Total;

    public Sales(String orderDate, int total) {
        OrderDate = orderDate;
        Total = total;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String orderDate) {
        OrderDate = orderDate;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }
}
