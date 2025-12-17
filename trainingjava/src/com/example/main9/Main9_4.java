package com.example.main9;
// 勇者の剣を装備した勇者を生成
public class Main9_4 {
    public static void main(String[] args) {
        Main9_3s s = new Main9_3s();
        s.name = "炎の剣";
        s.damage = 10;
        Main9_3h_8 h = new Main9_3h_8();
        h.name = "ミナト";
        h.hp = 100;
        h.sword = s;
        System.out.println("現在の武器は" + h.sword.name);   
    }
}
