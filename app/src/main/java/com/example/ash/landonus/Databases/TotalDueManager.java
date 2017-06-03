package com.example.ash.landonus.Databases;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by ash on 5/31/2017.
 */

public class TotalDueManager extends RealmObject {

    @PrimaryKey int id_1;
    int id, due;
    String flatTitle;
    String  user , monthYear;

    public int getDue() {
        return due;
    }

    public void setDue(int due) {
        this.due = due;
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

    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_1() {
        return id_1;
    }

    public void setId_1(int id_1) {
        this.id_1 = id_1;
    }
}
