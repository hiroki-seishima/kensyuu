package com.example.main14;

public class Main14_9_13_15 {
    String name;
    int hp;
    static int money;  //staticをつけることによって静的フィールド

    public static void setRandomMoney() {   //14_13で追加
        Main14_9_13_15.money = (int)(Math.random()*1000); 
        // System.out.println(this.name + "たちの所持金を初期化しました");  //14_15 エラーがでる。インスタンス生成なしで呼び出せるメソッドなのに自分自身のインスタンスのメンバであるthis.nameの処理ができない。ちなみにthisは非静的メンバ
    }
}
