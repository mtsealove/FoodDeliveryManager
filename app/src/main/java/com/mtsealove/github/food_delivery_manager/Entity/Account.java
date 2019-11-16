package com.mtsealove.github.food_delivery_manager.Entity;

import java.io.Serializable;

public class Account implements Serializable {
    String ID, UserName, BusinessName, BusinessNum, BusinessAddress, ProfileImage;

    public Account(String ID, String userName, String businessName, String businessNum, String businessAddress, String profileImage) {
        this.ID = ID;
        UserName = userName;
        BusinessName = businessName;
        BusinessNum = businessNum;
        BusinessAddress = businessAddress;
        ProfileImage = profileImage;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getBusinessName() {
        return BusinessName;
    }

    public void setBusinessName(String businessName) {
        BusinessName = businessName;
    }

    public String getBusinessNum() {
        return BusinessNum;
    }

    public void setBusinessNum(String businessNum) {
        BusinessNum = businessNum;
    }

    public String getBusinessAddress() {
        return BusinessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        BusinessAddress = businessAddress;
    }

    public String getProfileImage() {
        return ProfileImage;
    }

    public void setProfileImage(String profileImage) {
        ProfileImage = profileImage;
    }

    @Override
    public String toString() {
        return "Account{" +
                "ID='" + ID + '\'' +
                ", UserName='" + UserName + '\'' +
                ", BusinessName='" + BusinessName + '\'' +
                ", BusinessNum='" + BusinessNum + '\'' +
                ", BusinessAddress='" + BusinessAddress + '\'' +
                ", ProfileImage='" + ProfileImage + '\'' +
                '}';
    }
}
