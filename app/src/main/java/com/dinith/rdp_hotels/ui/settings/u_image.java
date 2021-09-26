package com.dinith.rdp_hotels.ui.settings;

public class u_image {
    private String mImageUrl;
    private String mName;
    public u_image() {
    }
    public u_image(String imageUrl,String username) {
        mImageUrl = imageUrl;
        mName = username;
    }
    public String getImageUrl() {
        return mImageUrl;
    }
    public void setImageUrl(String imageUrl) {
        mImageUrl = imageUrl;
    }
    public String getusername() {
        return mName;
    }
    public void setusername(String username) {
        mName = username;
    }
}
