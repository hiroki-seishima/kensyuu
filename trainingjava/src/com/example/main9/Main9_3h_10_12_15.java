package com.example.main9;

public class Main9_3h_10_12_15 {
    String name;
    int hp;
    Main9_3s sword;

    public void attack() {
        System.out.println(this.name + "は攻撃した！");
        System.out.println("敵に５ポイントのダメージをあたえた！");
    }

    public Main9_3h_10_12_15(String name) {
        this.hp = 100;    //hpフィールドを１００で初期化
        this.name = name;

    }
    // public Main9_3h_10_12() {
    //     this.hp = 100;
    //     this.name = "ダミー";

    public Main9_3h_10_12_15() {
        this("ダミー");
    }
}
