package com.example.main15;

import java.util.Calendar;
import java.util.Date;

public class Main15_12 {
    public static void main(String[] args){
        Calendar c = Calendar.getInstance();  //６つのint値からDateインスタンスを生成
        c.set(2019,8,22,1,23,45);  //日付入力
        c.set(Calendar.MONTH,9);  //10月に変更
        Date d = c.getTime();   //getTimeでcを呼び出す
        System.out.println(d);
        Date now = new Date();  //Dateインスタンンスからint値を生成
        c.setTime(now);
        int y = c.get(Calendar.YEAR);  //年を呼び出す
        System.out.println("今年は" + y + "年です");
    }
}
