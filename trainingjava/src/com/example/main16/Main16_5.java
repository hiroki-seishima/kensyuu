package com.example.main16;

import java.util.Set;
import java.util.TreeSet;

public class Main16_5 {
    public static void main(String[] args) {
        Set<String> words = new TreeSet<String>();    //TreeSetをインスタンス生成
        words.add("dog");   //Setを追加
        words.add("cat");
        words.add("wolf");
        words.add("panda");

        for(String w: words) {    //拡張for文ですべてをとりだす。
            System.out.print(w + "→");  //実行結果よりアルファベッド順になっていた。
        }
    }
}
