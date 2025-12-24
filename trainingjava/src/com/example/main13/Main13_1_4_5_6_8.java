package com.example.main13;
//ヒーロークラス
public class Main13_1_4_5_6_8 {
    private int hp;  //privateをつけることでHero以外でアクセスできなくなった。
    private String name;
    // Sword sword;　　//クラスをつくってないし、呼び出してもいないため、コメント表示

    public void bye() {
        System.out.println("勇者は別れを告げた");
    }
    private void die() {   //dieメゾッドは外部クラスから呼び出せなくなった。
        System.out.println(this.name + "は死んでしまった！");
        System.out.println("GAMEOVERです。");
    }

    void sleep() {     //publicを外すことで同一パッケージ内であればアクセス可能（この場合はmain13内でのアクセスが可能）
        this.hp = 100;
        System.out.println(this.name + "は眠って回復した！" );
    }

    public void attack(Matango m) {
        System.out.println(this.name + "の攻撃！");
        System.out.println("敵に10ポイントのダメージ");
        m.hp -= 10;

        System.out.println("お化けキノコ" + m.suffix + "から２ポイントの反撃を受けた");
        this.hp -= 2;

        if (this.hp <= 0) {
            this.die();
        }
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
