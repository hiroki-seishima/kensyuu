package com.example.main16;

import java.util.*;

class Hero {                //Heroクラス
    public String name;      // nameフィールド

}

public class Main16_8 {
    public static void main(String[] args) {
        Hero h = new Hero();     //Heroクラスをつかって変数hのインスタンス生成
        h.name = "ミナト";
        List<Hero> list = new ArrayList<Hero>();   //ArrayListのインスタンス生成　<>はクラスの型をいれるのでHeroとなる
        list.add(h);    //変数hをリストで追加
        h.name = "スガワラ";   //その後、hを上書き
        System.out.println(list.get(0).name);   //list.get(0)は変数hであるため、h.nameとなる
    }
    
}
