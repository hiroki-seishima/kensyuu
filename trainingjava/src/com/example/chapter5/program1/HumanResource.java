package com.example.chapter5.program1;
//人事クラス
public class HumanResource extends Employee{
    boolean result;  //採用結果が欲しいためにboolean型のresult属性を追加
    BT bt;    //BTクラスのインスタンス

    public HumanResource(String name,boolean result,BT bt) {  //コンストラクタ
        this.name = name;
        this.department ="人事";
        this.result = result;
        
        this.bt = bt;  //BTクラスのインスタンスを受け取る
    }

    public void syainMensestu() {

        if (result == true ) {
            System.out.println("「面接を行い、結果は採用だった」");
            this.bt.jyugyoinSakusei(name);  //
            //従業員作成メゾッドを使用し従業員を採用。リストに追加
        }else{
            System.out.println("「面接を行い、結果は不採用だった」");
        }
    }

    public void kyuyokeisan() {
        System.out.println("「給与計算を行った」");
    } 

    public void hyoji() {
        System.out.println("「{"+this.name+"}：{"+this.department+"}」");

    }
    
}
