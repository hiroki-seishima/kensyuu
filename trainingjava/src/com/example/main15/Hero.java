package com.example.main15;

public class Hero {

    private String name;
    private String job;
    private int gold;

    public Hero(){
        name = "minato";
        job = "hero";
        gold = 280;
    }

    public String getName(){
        return this.name;
    }
    public String getJob(){
        return this.job;
    }
    public int getGold(){
        return this.gold;
    }
}
