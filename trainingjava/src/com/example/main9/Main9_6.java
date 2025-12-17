package com.example.main9;
// 勇者２人と魔法使いを生成
public class Main9_6 {
    public static void main(String[] args) {
        Main9_3h_8 h1 = new Main9_3h_8();
        h1.name = "ミナト";
        h1.hp =100;
        Main9_3h_8 h2 = new Main9_3h_8();
        h2.name = "アサカ";
        h2.hp = 100;
        Main9_5 w = new Main9_5();
        w.name = "スガワラ";
        w.hp = 50;
        w.heal(h1);
        w.heal(h2);
        w.heal(h2);



    }
}
