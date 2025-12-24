package com.example.main12;

public class Hero extends Main12_2c{
    public void attack(Matango m) {
        System.out.println(this.name + "の攻撃！");
        System.out.println("敵に10ポイントのダメージ");
        m.hp -= 10;
    }
}
