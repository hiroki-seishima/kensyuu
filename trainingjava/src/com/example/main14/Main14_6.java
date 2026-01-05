package com.example.main14;

public class Main14_6 {
    public static void main(String[] args){
        Main14_4_5h h1 = new Main14_4_5h();
        h1.name = "ミナト";
        h1.hp = 100;
        Main14_4_5h h2 = new Main14_4_5h();
        h2.name = "ミナト";
        h2.hp = 100;
        if (h1.equals(h2) == true){
            System.out.println("同じ内容です");
        }else{
            System.out.println("違う内容です");
        }
    }
}
