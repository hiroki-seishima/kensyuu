package com.example.main16;

import java.util.*;  // import java.util.ArrayList;、次の行にimport java.util.Iterator;でも成立。複数ありutilで統一しているためまとめたものが表記のとおり。

public class Main16_2 {
    public static void main(String[] args) {
        ArrayList<String> names = new ArrayList<String>();   //インスタンス生成
        names.add("湊");  //リスト作成
        names.add("朝香");
        names.add("菅原");

        Iterator<String> it = names.iterator();  //イテレータのインスタンス生成
        while (it.hasNext()) {   //矢印を次に進められるなら繰り返す。
            String e = it.next();   //矢印を次に進め、内容を取り出す。
            System.out.println(e);  //取り出したもの用いた処理
        }
    }
}
