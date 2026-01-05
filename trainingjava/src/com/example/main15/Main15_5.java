package com.example.main15;

public class Main15_5 {
    public static void main(String[] args){
        //mainで確認したい
    }
    public boolean isValidPlayerName(String name){
        if(name.length() !=8){
            return false;
        }
        char first = name.charAt(0);      //charはシングルクォーテーション' 'となる
        if (!(first >= 'A' && first <= 'Z')){
            return false;
        }
        for (int i = 1; i < 8; i++){
            char c =name.charAt(i);
            if(!((c >= 'A' && c <= 'Z') || ( c >='0' && c <= '9'))){
                return false;
            }
        }
        return true;
    }
}
