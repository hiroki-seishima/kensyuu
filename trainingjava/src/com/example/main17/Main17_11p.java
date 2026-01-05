package com.example.main17;

public class Main17_11p {
    int age ;
    public void setAge(int age){
        if (age<0){
            throw new IllegalArgumentException("年齢は0以上の数を指定すべきです。指定値=" + age);
        }
        this.age = age;
    }
}
