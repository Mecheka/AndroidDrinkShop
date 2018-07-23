package com.example.suriya.androiddrinkshop.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DrinkModel {

    @SerializedName("ID")
    @Expose
    private String iD;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Link")
    @Expose
    private String link;
    @SerializedName("Price")
    @Expose
    private String price;
    @SerializedName("MenuID")
    @Expose
    private String menuID;

    public String getID() {
        return iD;
    }

    public void setID(String iD) {
        this.iD = iD;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getMenuID() {
        return menuID;
    }

    public void setMenuID(String menuID) {
        this.menuID = menuID;
    }

}
