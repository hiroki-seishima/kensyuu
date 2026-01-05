package com.example.main17;

import java.io.*;

public class Main17_1_2 {
    public static void main(String[] args){
        try{
            FileWriter fw = new FileWriter("data.txt");
        } catch (IOException e){
            System.out.println("エラーが発生しました。");
        }
        
    }
}
