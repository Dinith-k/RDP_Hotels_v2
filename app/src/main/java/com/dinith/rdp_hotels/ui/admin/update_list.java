package com.dinith.rdp_hotels.ui.admin;

public class update_list {
    private String Food;
    private String Gym;
    private String ImageUrl;
    private String Name;
    private String Price;
    private String Rate;
    private String Room_type;
    private String Transport;
    private String Wifi;

    public update_list() {
    }

    public update_list(String food,String gym,String imageUrl,String name,String price,String rate,String room_type,String transport,String wifi) {
        Gym = gym;
        Food = food;
        ImageUrl = imageUrl;
        Name = name;
        Price = price;
        Rate = rate;
        Room_type = room_type;
        Transport = transport;
        Wifi = wifi;


    }



    public String getgym() {
        return Gym;
    }
    public void setgym(String gym) {
        Gym = gym;
    }


    public String getfood() {
        return Food;
    }
    public void setfood(String food) {
        Food = food;
    }


    public String getimageUrl() {
        return ImageUrl;
    }
    public void setimageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }


    public String getname() {
        return Name;
    }
    public void setname(String name) {
        Name = name;
    }

    public String getprice() {
        return Price;
    }
    public void setprice(String price) {
        Price = price;
    }


    public String getrate() {
        return Rate;
    }
    public void setrate(String rate) {
        Rate = rate;
    }


    public String getwifi() {
        return Wifi;
    }
    public void setwifi(String wifi) {
        Wifi = wifi;
    }



    public String getroom_type() {
        return Room_type;
    }
    public void setroom_type(String room_type) {
        Room_type = room_type;
    }



    public String gettransport() {
        return Transport;
    }
    public void settransport(String transport) {
        Transport = transport;
    }



}
