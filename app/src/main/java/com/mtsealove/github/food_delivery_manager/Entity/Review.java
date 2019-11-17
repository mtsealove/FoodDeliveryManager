package com.mtsealove.github.food_delivery_manager.Entity;

public class Review {
    String ReviewTime, Content, Name;

    public Review(String reviewTime, String content, String name) {
        ReviewTime = reviewTime;
        Content = content;
        Name = name;
    }

    public String getReviewTime() {
        return ReviewTime;
    }

    public void setReviewTime(String reviewTime) {
        ReviewTime = reviewTime;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }
}
