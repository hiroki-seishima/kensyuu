package com.example.chapter5.program1;
//人事クラス
public class HumanResource extends Employee{

    public HumanResource(String name) {  //コンストラクタ
        this.name = name;
        this.department ="人事";

    }

    boolean result = false;

    public void syainMensestu() {


        if (result == true ) {
            System.out.println("「面接を行い、結果は採用だった」");

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
