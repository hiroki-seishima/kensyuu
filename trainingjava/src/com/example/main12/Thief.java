package com.example.main12;

public class Thief extends Main12_2c{
    public void attack(Matango m) {
        System.out.println(this.name + "の攻撃！");
        System.out.println("敵に4ポイントのダメージ");
        m.hp -= 4;
    }
}
