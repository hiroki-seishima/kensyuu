package com.example.main15;

public class Main15_1 {
    public static void main(String[] args){
        String s1 = "スッキリJava";
        // String s1 = ""; //空文字の場合確認用
        String s2 = "Java";
        // String s2 = "java";  //equalsの動きを確認するため用
        String s3 = "java";

        if (s2.equals(s3)){  //内容が等しいかどうかを調べる
            System.out.println("s2とs3は等しい");  
        }
        if (s2.equalsIgnoreCase(s3)){   //大文字小文字を区別せずに内容が等しいがどうかを調べる
            System.out.println("s2とs3はケースを区別しなければ等しい");
        }
        System.out.println("s1の長さは" + s1.length() + "です");  //文字列長さを調べる　全角文字も半角文字も１文字としてカウント
        if (s1.isEmpty()) {   //空文字かどうかを調べる　(length == 0)でもできるが、こちらの方が直感的にとらえやすい
            System.out.println("s1は空文字です");
        }
    }
}
