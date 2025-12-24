package com.example.main15;

import java.time.*;
import java.time.format.*;

public class Main15_16 {
    public static void main(String[] args){
        //文字列からLocalDateを生成
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy/MM/dd");   //ここでは日付のパターンを定義したフォーマッターオブジェクトを作成
        LocalDate ldate = LocalDate.parse("2020/09/22", fmt);   //ここでparse()を使ってインスタンス生成、第１引数を解析対象の文字列、第２引数をフォーマッターオブジェクトとしてLocalDateとして扱うことができる
        //クラス 変数 = クラス.静的メゾッドでインスタンス生成が可能

        //1000日後を計算する
        LocalDate ldatep = ldate.plusDays(1000); //plusDaysというメゾッドを呼びだし、ldatepを生成
        String str = ldatep.format(fmt);   //上で生成したfmtをつかい文字列に変換
        System.out.println("1000日後は"  + str);  //文字列として出力

        //現在日付との比較
        LocalDate now = LocalDate.now();  //静的メゾッドnow()を呼び出してインスタンス化
        if (now.isAfter(ldatep))  { //今日がldatep=2023/06/19よりも後かどうかを判定。trueなら出力
            System.out.println("1000日後は過去日付です");  //今現在に比べて2023/6/19は過去日付
        }
    }
}
