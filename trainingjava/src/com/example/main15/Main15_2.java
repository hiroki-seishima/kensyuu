package com.example.main15;

public class Main15_2 {
    public static void main(String[] args){
        String s1 = "Java and JavaScript";
        if (s1.contains("Java")){
            System.out.println("文字列s1は、Javaを含んでいます");
        }
        if (s1.endsWith("Java")) {
            System.out.println("文字列s1は、Javaが末尾にあります");
        }
        System.out.println("文字列s1で最初にJavaが登場する位置は" + s1.indexOf("Java"));  //前から最初に出た時のJavaの始めの文字この場合はJavaのJが何番目にあるかの確認。
        System.out.println("文字列s1で最後にJavaが登場する位置は" + s1.lastIndexOf("Java")); //後ろから最初にJavaの始めの文字この場合はJavaScriptのJavaのJが何番目にあるかの確認。ただしカウントは後ろからではなく前から数える


    }
}
