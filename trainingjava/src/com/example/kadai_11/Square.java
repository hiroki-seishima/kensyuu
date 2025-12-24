package com.example.kadai_11;

public class Square extends Rectangle{
    public Square(int x,int y,int width){ 
        super(x,y,width,width);   //スーパークラスRectangleのコンストラクタを明示的に呼び出す。引数は以下のとおりとする。
        // 第１引数Point型データのx座標、第２引数Point型データのy座標、第３引数正方形の一辺の長さ、第４引数正方形の一辺の長さ。
        // →親クラスから呼び出すのはsuper
    }
    public void draw() {
        System.out.println("[正方形を描画]点(" + p.getX() + "," + p.getY() +")を基準として幅・高さ" + width + "の正方形");
    }


}
