package com.example.appdatdoan.model;

public class User {
    private String IdUser;
    private String UserName;
    private String UserEmail;
    private String PasswordUser;
    private String UserPhone;

    public User() {
    }

    public User(String idUser, String userName, String userEmail, String passwordUser, String userPhone) {
        IdUser = idUser;
        UserName = userName;
        UserEmail = userEmail;
        PasswordUser = passwordUser;
        UserPhone = userPhone;
    }

    public String getIdUser() {
        return IdUser;
    }

    public void setIdUser(String idUser) {
        IdUser = idUser;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getPasswordUser() {
        return PasswordUser;
    }

    public void setPasswordUser(String passwordUser) {
        PasswordUser = passwordUser;
    }

    public String getUserPhone() {
        return UserPhone;
    }

    public void setUserPhone(String userPhone) {
        UserPhone = userPhone;
    }
}
