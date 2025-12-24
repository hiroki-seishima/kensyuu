package com.example.main12;
//ウィザードのクラス（キャラクターの抽象クラスを継承）
public class Main12_2w extends Main12_2c{
    int mp;
    public void attack(Matango m) {
        System.out.println(this.name + "の攻撃！");
        System.out.println("敵に３ポイントのダメージ");
        m.hp -= 3;
    }

    public void fireball(Matango m) {
        System.out.println(this.name + "は火の玉を放った！");
        System.out.println("敵に２０ポイントのダメージ");
        m.hp -= 20;
        this.mp -= 5;

    }
}
