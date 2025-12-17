package com.example.main9;
// 勇者のクラス
public class Main9_3h_8 {
    String name;
    int hp;
    Main9_3s sword;

    public void attack() {
        System.out.println(this.name + "は攻撃した！");
        System.out.println("敵に５ポイントのダメージをあたえた！");
    }

    public Main9_3h_8() {
        this.hp = 100;    //hpフィールドを１００で初期化
        

    }
}
