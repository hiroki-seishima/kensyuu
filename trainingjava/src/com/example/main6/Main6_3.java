package com.example.main6;

public class Main6_3 {
    public static void main(String[] args) {
        int a = 10;
        int b = 2;
        int total = tasu(a,b);  //同じクラスにメゾッドがないことからエラーが出る、bも同様
        int delta = hiku(a,b);
        System.out.println("足すと" + total + "、引くと" + delta);
    }
}
