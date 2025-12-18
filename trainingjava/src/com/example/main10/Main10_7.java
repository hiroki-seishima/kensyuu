package com.example.main10;

public class Main10_7 {
    String name = "ミナト";
    int hp = 100;
//攻撃
    public void attack(Matango m) {
        System.out.println(this.name + "の攻撃！");
        m.hp -= 5;
        System.out.println("５ポイントのダメージをあたえた！");
    }
//転倒
    public final void slip() {
        this.hp -= 5;
        System.out.println(this.name + "は転んだ！");
        System.out.println("５のダメージ");
    }
//逃走
    public void run() {
        System.out.println(this.name + "は逃げ出した！");
    }
}
