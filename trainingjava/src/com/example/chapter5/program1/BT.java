package com.example.chapter5.program1;

import java.util.*;  //リストを作成するのに外部インポート
//BTクラス
public class BT {
    final String company = "BT";   //会社名　BT 固定

    List<String> jl = new ArrayList<String>();    //従業員リストのインスタンスを生成

    List<String> bl = new ArrayList<String>();    //部署リストのインスタンスを生成
    
    
    void jyugyoinSakusei(String name){   //従業員作成メゾッド 従業員リスト作成
        jl.add(name);
    }

    public void busyoSakusei(){
        bl.add("人事部");
        bl.add("営業部");
        bl.add("エンジニア");
    }
    public void jHyoji(){
        //従業員リスト表示
        System.out.println("---------従業員リスト---------");
        for (String jlAll : jl) {   //for文をつかって全従業員を出力
            System.out.println(jlAll);
        }
        //部署リストを表示
        System.out.println("---------部署リスト-----------");
        for (String blAll : bl) {
            System.out.println(blAll);
        }
    }
}
