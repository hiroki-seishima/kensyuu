package com.example.main16;

import java.util.HashSet;   //外部インポート
import java.util.Set;

public class Main16_4 {
    public static void main(String[] args) {
        Set<String> colors = new HashSet<String>();  //インスタンス生成
        colors.add("赤");   //Set追加
        colors.add("青");
        colors.add("黄");

        for (String s :colors) {  //拡張for文で順番にすべて取り出す。
            System.out.print(s + "→");  //上から赤、青、黄とセットしたが、実行結果が青→赤→黄と出力された
        }
    }
    
}
