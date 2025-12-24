package com.example.main16;

import java.util.*;  //外部インポート

public class Main16_7 {
    public static void main(String[] args) {
        Map<String,Integer> prefs = new HashMap<String,Integer>();  //HashMapのインスタンス生成
        prefs.put("京都府",255);  //キーと値を入力し、格納
        prefs.put("東京都",1261);
        prefs.put("熊本剣",182);

        for (String key:prefs.keySet()) {    // 県名一覧を取得して繰り返す
            int value = prefs.get(key);     //県名（キー）を指定し人口（値）を取得する。
            System.out.println(key + "の人口は、" + value);   //取得したキーと値をつかって処理
        }
    }
}
