package com.example.main14;

public class Main14_11 {
    public static void main(String[] args){
        Main14_9_13_15 h1 = new Main14_9_13_15();
        Main14_9_13_15 h2 = new Main14_9_13_15();
        Main14_9_13_15.money = 100;   //共有フィールドのものを呼び出す。

        System.out.println(Main14_9_13_15.money);
        System.out.println(h1.money);
        h1.money = 300;

        Main14_9_13_15.setRandomMoney();   //Main14_13でh2.moneyがどう動くかを確認
        System.out.println(h2.money);

        
    }
}
