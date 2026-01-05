package com.example.main15;

public class Main15_9 {
    public static void main(String[] args){
        final String FORMAT = "%-9s %-13s 所持金%,6d";   //９文字幅、１３文字幅、６文字幅
        Hero hero = new Hero();
        
        String s = String.format(FORMAT,hero.getName(),hero.getJob(),hero.getGold());
        System.out.println(s);
    }

}
