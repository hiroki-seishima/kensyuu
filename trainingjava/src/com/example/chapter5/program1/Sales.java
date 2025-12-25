package com.example.chapter5.program1;
//営業クラス
public class Sales extends Employee {

    public Sales(String name) {  //コンストラクタ
        this.name = name;
        this.department = "営業";
    }
    public void syuho() {
        System.out.println("「週報の返信をした」");   //週報返信
    }

    public void mendan(Engineer engineer) {
        System.out.println("「{"+engineer.department+"."+engineer.name+"}}の面談を組んだ」");  //エンジニア.名前

    } 

    public void uchiawase(){
        System.out.println("「新規の打ち合わせをした」");
    }

    public void hyoji(){
        System.out.println("「{"+this.name+"}：{"+this.department+"}」");
    }
    
}
