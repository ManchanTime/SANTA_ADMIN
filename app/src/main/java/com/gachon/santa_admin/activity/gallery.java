package com.gachon.santa_admin.activity;

public class gallery {
    private String name;
    private String date;
    private int resId;

    public gallery(String name, String date, int resId) {
        this.name = name;
        this.date = date;
        this.resId = resId;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }


}
