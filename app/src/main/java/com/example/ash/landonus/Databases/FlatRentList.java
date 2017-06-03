package com.example.ash.landonus.Databases;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ash on 5/30/2017.
 */

public class FlatRentList  extends RealmObject{
    String flatTitle;
    int id;
    @PrimaryKey String rent;

    public String getFlatTitle() {
        return flatTitle;
    }

    public void setFlatTitle(String flatTitle) {
        this.flatTitle = flatTitle;
    }

    public String getRent() {
        return rent;
    }

    public void setRent(String rent) {
        this.rent = rent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
