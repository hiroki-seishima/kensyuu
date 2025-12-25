package com.example.chapter5.program1;
//エンジニアクラス
public class Engineer extends Employee {
    String gengo;

    public Engineer(String name,String gengo){  //コンストラクタ
        this.name = name;
        this.department = "エンジニア";
        this.gengo = gengo;
    }

    public void zishi() {
        System.out.println("「"+this.gengo+"を行った」");   //行った開発言語を出力する
    }

    public void hyoji(){
        System.out.println("「{"+this.name+"}：{"+this.department+"} 使用言語：{"+this.gengo+"}」");    //名前、所属部署、使用言語を出力
    }
    
}
