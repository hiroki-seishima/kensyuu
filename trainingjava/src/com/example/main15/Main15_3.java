package com.example.main15;

public class Main15_3 {
    public static void main(String[] args){
        String s1 = "Java programming";
        System.out.println("文字列s1の4文字目以降は" + s1.substring(3));  //a programmingが表示
        System.out.println("文字列s1の4〜8文字目は" + s1.substring(3,8));   //a proが表示（スペースも１文字として扱う）
    }
}
