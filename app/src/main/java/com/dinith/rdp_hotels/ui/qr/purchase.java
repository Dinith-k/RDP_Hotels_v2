package com.dinith.rdp_hotels.ui.qr;

public class purchase {
    private String menu_items;
    private String room_key;
    private String date;
    private String total_price;
    public purchase() {
    }
    public String getdate() {
        return date;
    }
    public void setdate(String date) {
        this.date = date;
    }


    public String getmenu_items() {
        return menu_items;
    }
    public void setmenu_items(String menu_items) {
        this.menu_items = menu_items;
    }


    public String getroom_key() {
        return room_key;
    }
    public void setroom_key(String room_key) {
        this.room_key = room_key;
    }


    public String gettotal_price() {
        return total_price;
    }
    public void settotal_price(String total_price) {
        this.total_price = total_price;
    }


}
