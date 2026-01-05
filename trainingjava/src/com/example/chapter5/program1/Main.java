package com.example.chapter5.program1;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        
        BT bt = new BT();   //BTクラスでインスタンス生成、リスト出力の呼び出し、人事でBTクラスのjyugyoinSakuseiを受け渡しする。
        bt.busyoSakusei(); 
        
        Scanner sc = new Scanner(System.in);
        System.out.println("面接する人数は？");
        int x = sc.nextInt();//面接参加人数を入力
        sc.nextLine();
        System.out.println(x + "人面接に来た");

        for (int z=0; z<x; z++){
            System.out.println(z+1 + "人目");

            System.out.println("従業員の名前は？");
            String name = sc.nextLine();  //従業員名キーボード入力
            System.out.println("採用結果は？ 採用：true 不採用：false");
            boolean result = sc.nextBoolean();//人事係で採用か不採用かをtrueかfalseで判断
            sc.nextLine();
            
            if (result == true){
                System.out.println("採用します。従業員リストに追加しました。所属部署はどうしますか？ 部署名：1人事、2営業、3エンジニア");
                int i = sc.nextInt();//配属先部署を入力
                sc.nextLine();//case3を選択後にgengoの入力が自動的に空打ちになることから入力
                

                switch(i){   //部署パターン
                    case 1:
                        HumanResource h = new HumanResource(name,true,bt);
                        h.syainMensestu();
                        h.kyuyokeisan();
                        h.hyoji();

                        System.out.println("従業員の名前："+name+" 採用結果：採用 部署名：人事");
                        break;

                    case 2:
                        Sales s = new Sales(name);
                        HumanResource hs = new HumanResource(name,true,bt);
                        
                        
                        System.out.println("打ち合わせをするエンジニアの情報");
                        System.out.println("打ち合わせをするエンジニアの名前は？");
                        String dename = sc.nextLine();  //打ち合わせエンジニアの名前
                        System.out.println("打ち合わせをするエンジニアの使用言語は？");
                        String degengo = sc.nextLine(); //打ち合わせエンジニアの言語
                        Engineer de = new Engineer(dename,degengo);//打ち合わせをするエンジニアのインスタンス生成
                        s.syuho();
                        s.mendan(de);
                        s.uchiawase();
                        s.hyoji();
                        hs.syainMensestu();

                        System.out.println("従業員の名前："+name+" 採用結果：採用 部署名：営業");
                        break;
                    case 3:
                        System.out.println("開発言語は？");
                        String gengo = sc.nextLine();   //言語を入力

                        Engineer e = new Engineer(name,gengo);
                        HumanResource he = new HumanResource(name, true, bt);
                        e.zishi();
                        e.hyoji();
                        he.syainMensestu();

                        System.out.println("従業員の名前："+name+" 採用結果：採用 部署名：エンジニア 言語："+gengo);
                        break;
                }      
            }else{
                System.out.println("従業員の名前："+name+" 採用結果：不採用");
            }
        }
        sc.close();

        bt.jHyoji();
       
        
    }
}