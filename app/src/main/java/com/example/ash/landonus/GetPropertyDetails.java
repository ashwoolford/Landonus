package com.example.ash.landonus;

import io.realm.RealmObject;

/**
 * Created by ash on 5/18/2017.
 */

public class GetPropertyDetails extends RealmObject{
    String ImageUri;
    String title;
    String address;

    public String getImageUri() {
        return ImageUri;
    }

    public void setImageUri(String imageUri) {
        ImageUri = imageUri;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
