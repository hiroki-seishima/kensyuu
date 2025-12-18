package com.example.main2;

public class Main2_15 {
    public static void main(String[] args){
        System.out.println("あなたの名前を入力してください。");
        // String name = new java.util.Scanner(System.in).nextLine();
        // System.out.println("あなたの年齢を入力してください。");
        // int age = new java.util.Scanner(System.in).nextInt();
        // System.out.println("ようこそ、" + age + "歳の" + name + "さん");

        //変数に代入せずに即座にメソッドを呼び出す場合、作成された Scanner オブジェクトが自動的に閉じられずメモリリークの可能性があると判断された。動きはするが、エラーが出る。以下のやり方が推奨されるとのこと。

        try(java.util.Scanner sc =  new java.util.Scanner(System.in)){
            String name = sc.nextLine();
            System.out.println("あなたの年齢を入力してください。");
            int age = sc.nextInt();
            System.out.println("ようこそ、" + age + "歳の" + name + "さん");
        }
            
    }
}
