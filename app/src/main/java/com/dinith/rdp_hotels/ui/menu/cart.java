package com.dinith.rdp_hotels.ui.menu;

public class cart {
    private String quantity;
    private String name;
    private String image;
    private String desc;
    private String price;
    private String key;

    public cart(){}
    public String getname() {
        return name;
    }

    public void setname(String name) {
        this.name = name;
    }

    public String getquantity() {
        return quantity;
    }

    public void setquantity(String quantity) {
        this.quantity = quantity;
    }

    public String getkey() {
        return key;
    }

    public void setkey(String key) {
        this.key = key;
    }

    public String getimage() {
        return image;
    }

    public void setimage(String image) {
        this.image = image;
    }

    public String getdesc() {
        return desc;
    }

    public void setdesc(String desc) {
        this.desc = desc;
    }


    public String getprice() {
        return price;
    }

    public void setprice(String price) {
        this.price = price;
    }

}
