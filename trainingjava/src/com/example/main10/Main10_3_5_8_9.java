package com.example.main10;

public class Main10_3_5_8_9 extends Main10_1 {
    boolean flying;

    public void flying(){
        this.flying = true;
        System.out.println("飛び上がった！");
    }

    public void land() {
        this.flying =false;
        System.out.println("着地した！");
    }
    public void run() {
        System.out.println(this.name + "は撤退した");
    }
    public void attack(Matango m) {
        // System.out.println(this.name + "の攻撃！");
        // m.hp -= 5;
        // System.out.println("５ポイントのダメージをあたえた！");
        // if (this.flying) {
        //     System.out.println(this.name + "の攻撃！");
        //     m.hp -=5;
        //     System.out.println("５ポイントのダメージをあたえた！");
        super.attack(m);
        if (this.flying) {
            super.attack(m);
        }
    }
}
