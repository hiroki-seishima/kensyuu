package com.example.main16;

import java.util.HashSet;  //外部インポート
import java.util.Set;

public class Main16_3 {
    public static void main(String[] args) {
        Set<String> colors = new HashSet<String>();  //HashSetのインスタンス生成　ArrayListのときと同じ定義
        colors.add("赤");  //Setを追加
        colors.add("青");
        colors.add("黄");
        colors.add("赤");  //実行結果から重複は反映されない。
        System.out.println("色は" + colors.size() + "種類");  //変数.size()で要素数を返す
    }
}
