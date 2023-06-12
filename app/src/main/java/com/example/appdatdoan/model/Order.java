package com.example.appdatdoan.model;

import java.util.List;

public class Order {
    private int IdOrder;
    private List<DetailCart> lstCart;
    private String IdUser;
    private String NameUser;
    private String Address;
    private String PhoneNumber;
    private String Payment;
    private String Status;
    private Integer SumPriceOrder;
    private String Time;

    public Order() {
    }

    public Order(int idOrder, List<DetailCart> lstCart, String idUser, String nameUser, String address, String phoneNumber, String payment, String status, Integer sumPriceOrder, String time) {
        IdOrder = idOrder;
        this.lstCart = lstCart;
        IdUser = idUser;
        NameUser = nameUser;
        Address = address;
        PhoneNumber = phoneNumber;
        Payment = payment;
        Status = status;
        SumPriceOrder = sumPriceOrder;
        Time = time;
    }

    public int getIdOrder() {
        return IdOrder;
    }

    public void setIdOrder(int idOrder) {
        IdOrder = idOrder;
    }

    public List<DetailCart> getLstCart() {
        return lstCart;
    }

    public void setLstCart(List<DetailCart> lstCart) {
        this.lstCart = lstCart;
    }

    public String getIdUser() {
        return IdUser;
    }

    public void setIdUser(String idUser) {
        IdUser = idUser;
    }

    public String getNameUser() {
        return NameUser;
    }

    public void setNameUser(String nameUser) {
        NameUser = nameUser;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getPayment() {
        return Payment;
    }

    public void setPayment(String payment) {
        Payment = payment;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public Integer getSumPriceOrder() {
        return SumPriceOrder;
    }

    public void setSumPriceOrder(Integer sumPriceOrder) {
        SumPriceOrder = sumPriceOrder;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getListDetailCart()
    {
        if(lstCart == null || lstCart.isEmpty())
        {
            return "";
        }else{
            StringBuilder stringBuilder = new StringBuilder();
            for(int i = 0; i < lstCart.size(); i++)
            {
                DetailCart detailCart = lstCart.get(i);
                if(stringBuilder.length() > 0)
                {
                    stringBuilder.append("\n");
                }
                stringBuilder.append(detailCart.getNameFoodTemp() + " x" + detailCart.getCount());
            }
            return stringBuilder.toString();
        }
    }
}
