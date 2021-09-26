package com.dinith.rdp_hotels.ui.home;

import com.google.firebase.database.Exclude;

public class  Upload {
    private String mName;
    private String mImageUrl;
    private String mKey;
    private String Place;
    private String mplceName;
    private String place_name;
    private String  Description;
    private String NameOfPlace;

    public Upload() {
    }
    public Upload(String name, String imageUrl ,String place,String description,String place_name) {
        if (name.trim().equals("")) {
            name = "No Name";
        }
        mName = name;
        mImageUrl = imageUrl;
        Place = place;
        Description = description;
        NameOfPlace = place_name;
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




}

