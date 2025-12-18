package com.example.main11;

public class Main11_5 extends Main11_2c {
    public void attack(Matango m) {
        System.out.println(this.name + "の攻撃！");
        System.out.println("敵に１０ポイントのダメージをあたえた！");
        m.hp -= 10;
    }

}
