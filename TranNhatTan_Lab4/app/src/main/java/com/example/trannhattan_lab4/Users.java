package com.example.trannhattan_lab4;


public class Users {
    private String UserName, FullName, Phone, Password;


    public Users(String userName, String fullName, String phone, String password) {
        UserName = userName;
        FullName = fullName;
        Phone = phone;
        Password = password;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
