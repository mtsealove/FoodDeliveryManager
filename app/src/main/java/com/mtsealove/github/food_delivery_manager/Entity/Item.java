package com.mtsealove.github.food_delivery_manager.Entity;

public class Item {
    String ItemName, ImagePath;
    int Price, Total, ID;

    public Item(String itemName, String imagePath, int price, int total, int ID) {
        ItemName = itemName;
        ImagePath = imagePath;
        Price = price;
        Total = total;
        this.ID = ID;
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

    public int getPrice() {
        return Price;
    }

    public void setPrice(int price) {
        Price = price;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
}
