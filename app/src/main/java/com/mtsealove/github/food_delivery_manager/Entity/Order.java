package com.mtsealove.github.food_delivery_manager.Entity;

public class Order {
    String OrderTime, Location, ItemName, ImagePath, StatusName;
    int OrderID, Price, status;

    public Order(String orderTime, String location, String itemName, String imagePath, String statusName, int orderID, int price, int status) {
        OrderTime = orderTime;
        Location = location;
        ItemName = itemName;
        ImagePath = imagePath;
        StatusName = statusName;
        OrderID = orderID;
        Price = price;
        this.status = status;
    }

    public String getOrderTime() {
        return OrderTime;
    }

    public void setOrderTime(String orderTime) {
        OrderTime = orderTime;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getItemName() {
        return ItemName;
    }

    public void setItemName(String itemName) {
        ItemName = itemName;
    }

    public String getImagePath() {
        return ImagePath;
    }

    public void setImagePath(String imagePath) {
        ImagePath = imagePath;
    }

    public String getStatusName() {
        return StatusName;
    }

    public void setStatusName(String statusName) {
        StatusName = statusName;
    }

    public int getOrderID() {
        return OrderID;
    }

    public void setOrderID(int orderID) {
        OrderID = orderID;
    }

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "OrderTime='" + OrderTime + '\'' +
                ", Location='" + Location + '\'' +
                ", ItemName='" + ItemName + '\'' +
                ", ImagePath='" + ImagePath + '\'' +
                ", StatusName='" + StatusName + '\'' +
                ", OrderID=" + OrderID +
                ", Price=" + Price +
                ", status=" + status +
                '}';
    }
}
