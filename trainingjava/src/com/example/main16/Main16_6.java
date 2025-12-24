package com.example.main16;

import java.util.*;   //外部インポート

public class Main16_6 {
    public static void main(String[] args) {
        Map<String,Integer> prefs = new HashMap<String,Integer>();  //インスタンス生成
        prefs.put("京都府",255);   //ペアで値を格納　put(キー,値)で追加
        prefs.put("東京都",1261);
        prefs.put("熊本県",181);

        int tokyo = prefs.get("東京都");  //キーを指定し値を取得
        System.out.println("東京都の人口は、" + tokyo);  //取得した値を使って処理
        prefs.remove("京都府");   //キーを選択して削除
        //京都の値が削除されたか確認　→ エラーがでた
        // int kyoto = prefs.get("京都府");
        // System.out.println(kyoto);
        prefs.put("熊本",182);   //キー、値を更新　重複したキーは許されないため、更新された。
        int kumamoto = prefs.get("熊本県");  //キーを指定し値を取得
        System.out.println("熊本県の人口は、" + kumamoto); //取得した値を使って処理
    }
}
