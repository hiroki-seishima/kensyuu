package com.example.main8;
// ヒーロークラス
public class Main8_2_3_4_7_8 {
    //以下、フィールド
    String name;
    int hp;
    int level = 10;

//眠るの操作
//staticがないのはthisがあるため
    public void sleep() {
        this.hp = 100;  //thisは自分自身のインスタンス（この場合は自分＝ヒーロー）
        System.out.println(this.name + "は、眠って回復した！");
    }
//座るの操作
    public void sit(int sec) {
        this.hp += sec;
        System.out.println(this.name + "は、" + sec + "秒座った！");
        System.out.println("HPが、" + sec + "ポイント回復した");
        //座った秒＝ポイント回復
    }
// 転ぶの操作
    public void slip() {
        this.hp -= 5;
        System.out.println(this.name + "は、転んだ！");
        System.out.println("５のダメージ！");
    }

// 逃げるの操作
    public void run() {
        System.out.println(this.name + "は、逃げ出した！");
        System.out.println("GAMEOVER");
        System.out.println("最終HPは" + this.hp + "でした");
    }
}

