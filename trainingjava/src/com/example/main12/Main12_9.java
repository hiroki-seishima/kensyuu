package com.example.main12;

public class Main12_9 extends Main12_2c{
    public void attack(Main12_5mo mo){
        System.out.println(this.name + "の攻撃！");
        System.out.println("敵に１０ポイントのダメージをあたえた！");
        mo.hp -= 10;
    }
    public void attack(Matango m){
        System.out.println(this.name + "の攻撃");
        System.out.println("敵に１０ポイントのダメージをあたえた！");
        m.hp -= 10;
    }
}
