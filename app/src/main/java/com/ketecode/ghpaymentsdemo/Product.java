package com.ketecode.ghpaymentsdemo;

import android.widget.ImageView;

import java.net.URL;

/**
 * Created by KobbyFletcher on 1/19/16.
 */
public class Product {
    String name;
    URL product;
    String category;

    public Product(int imageId, String imageIdString) {
        localResourceId = imageId;
        name = imageIdString;
    }

    public void setName(String name) {
        this.name = name;
    }

    ImageView theImage;

    public ImageView getTheImage() {
        return theImage;
    }

    public void setTheImage(ImageView theImage) {
        this.theImage = theImage;
    }

    int localResourceId;

    public Product(int resourceID){
        localResourceId = resourceID;
    }

    public Product() {

    }

    public String getName() {
        return name;
    }

    public int setLocalResourceId(int localResourceId) {
        this.localResourceId = localResourceId;
        return localResourceId;
    }

    public int getLocalResourceId() {
        return this.localResourceId;
    }
}

