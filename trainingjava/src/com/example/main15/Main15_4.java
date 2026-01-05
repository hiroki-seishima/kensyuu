package com.example.main15;

public class Main15_4 {
    public static void main(String[] args){
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <10000; i++) {
            sb.append("Java");
        }
        String s = sb.toString();
        System.out.println(s);   //出力して結果を確認
    }
}
