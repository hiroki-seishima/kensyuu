package com.example.main10;

public class Main10_1 {
    String name = "ミナト";
    int hp = 100;

    public void attack(Matango m) {
        System.out.println(this.name + "の攻撃！");
        m.hp -= 5;
        System.out.println("５ポイントのダメージをあたえた！");
    }

    public void run() {
        System.out.println(this.name + "は逃げ出した！");
    }
}
