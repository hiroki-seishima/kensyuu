package com.example.chapter5.program1;

import java.util.*;  //リストを作成するのに外部インポート
//BTクラス
public class BT {
    final String company = "BT";   //会社名　BT 固定

    List<Employee> jl = new ArrayList<Employee>();    //従業員リストのインスタンスを生成

    List<String> bl = new ArrayList<String>();    //部署リストのインスタンスを生成
    
    
    void jyugyoinSakusei(){   //従業員作成メゾッドで従業員のインスタンスを生成
        
        jl.add(new HumanResource("田中"));
        jl.add(new Sales("山田"));
        jl.add(new Engineer("佐藤","Java"));
    } 

    public void busyoSakusei(){
        bl.add("人事部");
        bl.add("営業部");
        bl.add("エンジニア");
    }
    public void jHyoji(){
        //for文をつかって全従業員を出力
    }
}
