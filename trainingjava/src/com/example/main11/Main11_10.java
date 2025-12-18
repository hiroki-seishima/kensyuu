package com.example.main11;

public class Main11_10 extends Main11_2c{
    public void dance() {
        System.out.println(this.name + "は情熱的に踊った");
    }
    public void attack(Matango m){
        System.out.println(this.name  + "の攻撃");
        System.out.println("敵に３ポイントのダメージ");
        m.hp -= 3;
    }
}
