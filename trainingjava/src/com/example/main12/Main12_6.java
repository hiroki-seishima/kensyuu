package com.example.main12;

public class Main12_6 {
    public static void main(String[] args) {
        Hero h1 = new Hero();
        Hero h2 = new Hero();
        Thief t1 = new Thief();
        Main12_2w w1 = new Main12_2w();
        Main12_2w w2 = new Main12_2w();

        // 宿に泊まる操作
        h1.hp += 50;
        h2.hp += 50;
        t1.hp += 50;
        w1.hp += 50;
        w2.hp += 50;
    }
}
// Hero,thiefはキャラクタークラスから継承したものを作成Hero.javaとThief.java
// また、キャラクターのattackが抽象化メゾットであり、未実装の状態であり継承した先で実装化する必要があったため、attackだけ追加。