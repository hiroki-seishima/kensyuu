package com.example.main15;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main15_13 {
    public static void main(String[] args) throws Exception{
        SimpleDateFormat f = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date d = f.parse("2020/09/22 01:23:45");  //parseで文字列を日付インスタンスにするためのもの
        System.out.println(d);
        Date now = new Date();
        String s = f.format(now);
        System.out.println("現在は" + s + "です");  //formatで日付から文字列に変換
    }
}
