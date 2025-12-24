package com.example.main12;

public class Main12_10 {
    public static void main(String[] args) {
        Main12_5mo[] monsters = new Main12_5mo[3];
        monsters[0] = new Main12_5sl();
        monsters[1] = new Goblin();
        monsters[2] = new DeathBat();
        for (Main12_5mo m : monsters) {
            m.run();
        }
    
    }
}
