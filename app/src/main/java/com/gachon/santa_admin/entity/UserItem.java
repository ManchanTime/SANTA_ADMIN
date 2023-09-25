package com.gachon.santa_admin.entity;

import androidx.appcompat.app.AppCompatActivity;


public class UserItem  {
    String name;
    String uid;
    String sex;
    String age;

    public UserItem(String name, String sex, String age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }

    public UserItem(String name, String age, String sex, String uid) {
        this.name = name;
        this.uid = uid;
        this.sex = sex;
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "SingerItem{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }
}