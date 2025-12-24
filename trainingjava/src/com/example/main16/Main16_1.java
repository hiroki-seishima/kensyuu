package com.example.main16;

import java.util.ArrayList;  //外部インポート

public class Main16_1 {
    public static void main(String[] args){
        ArrayList<Integer> points = new ArrayList<Integer>();   //pointという変数でインスタンス生成
        points.add(10);  //自動的にIntegerに変換・格納される
        points.add(80);
        points.add(75);
        for (int i: points) {  //拡張for文で、リスト化されたものをすべて順序通り取り出してSystem.out.plintln()で出力
            System.out.println(i);
        }
    }
}
