package com.example.main6;
import com.example.calcapp.logics.*;  //全クラスのインポート

public class Main6_9 {
    public static void main(String[] args) {
        int a =10;
        int b =2;
        int total = CalcLogic.tasu(a,b); //パッケージ名.クラス名.メゾッド
        int delta = CalcLogic.hiku(a,b);
        System.out.println("足すと" + total + "、引くと" + delta);
    }
}