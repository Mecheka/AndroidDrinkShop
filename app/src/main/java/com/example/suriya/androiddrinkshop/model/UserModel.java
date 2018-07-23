package com.example.suriya.androiddrinkshop.model;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

@Parcel
public class UserModel {

    private String phone;
    private String address;
    private String name;
    private String birthdate;
    private String error_msg;

    @ParcelConstructor
    public UserModel(){

    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getError_msg() {
        return error_msg;
    }

    public void setError_msg(String error_msg) {
        this.error_msg = error_msg;
    }
}
