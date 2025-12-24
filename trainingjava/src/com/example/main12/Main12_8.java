package com.example.main12;

public class Main12_8 extends Main12_2c{
    // キノコ攻撃
    public void attack(Matango m){
        System.out.println(this.name + "の攻撃");
        System.out.println("敵に１０ポイントのダメージをあたえた！");
        m.hp -= 10;
    }
    // ゴブリン攻撃
    public void attack(Goblin g){
        System.out.println(this.name + "の攻撃");
        System.out.println("敵に１０ポイントのダメージをあたえた！");
        g.hp -= 10;

    }
    // スライム攻撃
    public void attack(Main12_5sl sl){
        System.out.println(this.name + "の攻撃");
        System.out.println("敵に１０ポイントのダメージをあたえた！");
        sl.hp -= 10;
    }
    
}

// ゴブリンのクラスが必要(作成済)