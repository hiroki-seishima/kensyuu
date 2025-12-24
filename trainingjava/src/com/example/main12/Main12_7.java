package com.example.main12;

public class Main12_7 {
    public static void main(String[] args) {
        Main12_2c[] c = new Main12_2c[5];
        c[0] = new Hero();
        c[1] = new Hero();
        c[2] = new Thief();
        c[3] = new Main12_2w();
        c[4] = new Main12_2w();

        for (Main12_2c ch : c) {
            ch.hp += 50;
            System.out.println(ch.hp);
        }
    }
}
