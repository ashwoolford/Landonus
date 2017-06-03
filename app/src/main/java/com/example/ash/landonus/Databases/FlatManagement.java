package com.example.ash.landonus.Databases;

import io.realm.RealmObject;

/**
 * Created by ash on 5/22/2017.
 */

public class FlatManagement extends RealmObject{
    private String flatTitle, coordinatorName, phoneNumber;
    private int id;

    public String getFlatTitle() {
        return flatTitle;
    }

    public void setFlatTitle(String flatTitle) {
        this.flatTitle = flatTitle;
    }

    public String getCoordinatorName() {
        return coordinatorName;
    }

    public void setCoordinatorName(String coordinatorName) {
        this.coordinatorName = coordinatorName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
