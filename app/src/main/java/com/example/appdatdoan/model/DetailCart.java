package com.example.appdatdoan.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "detailcart1")
public class DetailCart implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private int Id;
    private int IdFoodTemp;
    private String IdUser;
    private String NameFoodTemp;
    private String ImgFoodTemp;
    private Integer PriceTemp;
    private int Count;
    private Integer TotalPrice;

    public DetailCart()
    {}

    public DetailCart(int idFoodTemp, String idUser, String nameFoodTemp, String imgFoodTemp, Integer priceTemp, int count, Integer totalPrice) {
        IdFoodTemp = idFoodTemp;
        IdUser = idUser;
        NameFoodTemp = nameFoodTemp;
        ImgFoodTemp = imgFoodTemp;
        PriceTemp = priceTemp;
        Count = count;
        TotalPrice = totalPrice;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getIdFoodTemp() {
        return IdFoodTemp;
    }

    public void setIdFoodTemp(int idFoodTemp) {
        IdFoodTemp = idFoodTemp;
    }

    public String getIdUser() {
        return IdUser;
    }

    public void setIdUser(String idUser) {
        IdUser = idUser;
    }

    public String getNameFoodTemp() {
        return NameFoodTemp;
    }

    public void setNameFoodTemp(String nameFoodTemp) {
        NameFoodTemp = nameFoodTemp;
    }

    public String getImgFoodTemp() {
        return ImgFoodTemp;
    }

    public void setImgFoodTemp(String imgFoodTemp) {
        ImgFoodTemp = imgFoodTemp;
    }

    public Integer getPriceTemp() {
        return PriceTemp;
    }

    public void setPriceTemp(Integer priceTemp) {
        PriceTemp = priceTemp;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    public Integer getTotalPrice() {
        return TotalPrice;
    }

    public void setTotalPrice(Integer totalPrice) {
        TotalPrice = totalPrice;
    }
}
