package com.example.main12;

public class Main12_3_4 {
    public static void main(String[] args) {
        Main12_2w w = new Main12_2w();
        // Main12_2c c = new Main12_2w();
        Matango m = new Matango();
        w.name = "アサカ";
        w.attack(m);
        w.fireball(m);
        // c.name = "アサカ";  //12_4
        // c.attack(m);      //12_4
        // c.fireball(m);    //12_4
    }
}
//変数cにMain12_2w(Wizard)にインスタンスをいれても実行きないことを確認するために行なったもの。エラーが発生するためコメント表示