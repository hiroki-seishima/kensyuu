package com.example.main4;

public class Main4_8 {
    public static void main(String[] args){
        int[] scores = {20,30,40,50,80};
        int sum = scores[0]+scores[1]+scores[2]+scores[3]+scores[4];
        int avg ;
        avg = sum / scores.length;
        System.out.println("合計点" + sum + "点");
        System.out.println("平均点" + avg + "点");

    }
}
