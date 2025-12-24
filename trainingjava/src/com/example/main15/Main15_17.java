package com.example.main15;

import java.time.*;

public class Main15_17 {
    public static void main(String[] args) {
        LocalDate d1 = LocalDate.of(2020,1,1);  //d1を日付表現でインスタンス化
        LocalDate d2 = LocalDate.of(2020,1,4);  //d2を日付表現でインスタンス化

        //3日間を表すPeriodを２通りの方法で生成
        Period p1 = Period.ofDays(3);   //３日間の期間をインスタンス生成
        Period p2 = Period.between(d1,d2);  //betweenの第１引数は開始日（その日含む）第２引数は終了日（その日含まない）までの期間をインスタンス生成


        //d2のさらに3日後を計算する
        LocalDate d3 = d2.plus(p2);

        //出力して確認する
        System.out.println(d1); 
        System.out.println(d2); 
        System.out.println(d3);
        System.out.println(p1); //３日間 P3Dで出力
        System.out.println(p2); //1日〜4日で終わり含まずの3日間 P3Dで出力
    }
}
