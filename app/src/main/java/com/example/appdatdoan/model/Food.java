package com.example.appdatdoan.model;

import java.io.Serializable;

public class Food implements Serializable {
    private int IdFood;
    private String FoodName;
    private Integer Price;
    private String Describe;
    private String ImageUrl;

    public Food() {
    }

    public Food(int idFood, String foodName, Integer price, String describe, String imageUrl) {
        IdFood = idFood;
        FoodName = foodName;
        Price = price;
        Describe = describe;
        ImageUrl = imageUrl;
    }

    public int getIdFood() {
        return IdFood;
    }

    public void setIdFood(int idFood) {
        IdFood = idFood;
    }

    public String getFoodName() {
        return FoodName;
    }

    public void setFoodName(String foodName) {
        FoodName = foodName;
    }

    public Integer getPrice() {
        return Price;
    }

    public void setPrice(Integer price) {
        Price = price;
    }

    public String getDescribe() {
        return Describe;
    }

    public void setDescribe(String describe) {
        Describe = describe;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
