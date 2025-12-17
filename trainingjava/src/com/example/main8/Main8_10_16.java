package com.example.main8;
//神様クラス
public class Main8_10_16 {
    public static void main(String[] args) {
        //勇者を生成
        Main8_2_3_4_7_8 h = new Main8_2_3_4_7_8(); //newがつくことによりHeroインスタンスを生成させる。この場合はMain8_以降省略
        //初期フィールドをセット
        h.name = "ミナト";
        h.hp = 100;

        //キノコを生成１体目
        Main8_5_6_15 m1 = new Main8_5_6_15();
        m1.hp = 50;
        m1.suffix = 'A';  //文字型charのため'。Stringなら"。

        //キノコを生成２体目
        Main8_5_6_15 m2 = new Main8_5_6_15();
        m2.hp = 48;
        m2.suffix = 'B';


        System.out.println("勇者" + h.name + "を生み出しました！");
        //勇者の操作
        h.sit(5);
        m1.run();
        m2.run();
        h.run();
    }
}
