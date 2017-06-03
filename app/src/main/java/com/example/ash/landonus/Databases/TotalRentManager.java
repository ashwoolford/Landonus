package com.example.ash.landonus.Databases;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ash on 5/31/2017.
 */

public class TotalRentManager extends RealmObject {
    int rent;
    int id;
    String flatTitle , user;
    String monthYear;

    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }

    public int getRent() {
        return rent;
    }

    public void setRent(int rent) {
        this.rent = rent;
    }


    public String getFlatTitle() {
        return flatTitle;
    }

    public void setFlatTitle(String flatTitle) {
        this.flatTitle = flatTitle;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
