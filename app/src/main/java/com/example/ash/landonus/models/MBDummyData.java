package com.example.ash.landonus.models;

/**
 * Created by ash on 5/26/2017.
 */

public class MBDummyData {
    private String flatName;
    private String paid;
    private Number due;

    public MBDummyData(String flatName, String paid, Number due) {
        this.flatName = flatName;
        this.paid = paid;
        this.due = due;
    }

    public String getFlatName() {
        return flatName;
    }

    public void setFlatName(String flatName) {
        this.flatName = flatName;
    }

    public String getPaid() {
        return paid;
    }

    public void setPaid(String paid) {
        this.paid = paid;
    }

    public Number getDue() {
        return due;
    }

    public void setDue(Number due) {
        this.due = due;
    }
}
