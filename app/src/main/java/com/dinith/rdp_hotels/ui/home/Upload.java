package com.dinith.rdp_hotels.ui.home;

import com.google.firebase.database.Exclude;

public class  Upload {
    private String mName;
    private String mImageUrl;
    private String mKey;
    private String price;
    private String rate;
    private String  Description;
    private String room_type;

    public Upload() {
    }
    public Upload(String name, String imageUrl,String description) {
        if (name.trim().equals("")) {
            name = "No Name";
        }
        mName = name;
        mImageUrl = imageUrl;

        Description = description;

    }
    public String getDescription() {
        return Description;
    }
    public void setDescription(String description) {
        Description = description;
    }







    public String getName() {
        return mName;
    }
    public void setName(String name) {
        mName = name;
    }


    public String getImageUrl() {
        return mImageUrl;
    }
    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }

    @Exclude
    public void setKey(String key){
        mKey = key;
    }


    public String getKey() {

            return mKey;
    }
    public String getrate() {

        return rate;
    }

    public String getprice() {
        return price;
    }

    public String getroom_type() {
        return room_type;
    }
}

