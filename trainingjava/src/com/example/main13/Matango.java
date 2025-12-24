package com.example.main13;

public class Matango {
    String name;
    int hp;
    final int LEVEL = 10; //finalをつけることのより変数→定数
    char suffix;
    
    //逃げる操作
    public void run() {
        System.out.println("お化けキノコ" + this.suffix + "は逃げ出した！");
    }  
}
