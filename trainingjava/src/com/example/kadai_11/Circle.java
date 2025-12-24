package com.example.kadai_11;

public class Circle extends Shape {   //親クラスShapeクラスを継承
    private Point center;   //円の中心を表すPoint型privateフィールド
    private int radius;    //円の半径を表すint型privateフィールド
    

    public Circle() {   //引数なしのコンストラクタの定義 center(x,y座標)、半径全て０で初期化する。
        this.center = new Point(0,0);
        this.radius = 0;
    }
    public Circle(int x,int y,int r){   //引数x,yで受け取ったデータを用いて、１つのPointオブジェクトを生成し、centerフィールドに代入
        this.center = new Point(x,y);
        this.radius = r;

    }
    public void draw() {
        System.out.println("[円を描画]中心点(" + center.getX() + "," + center.getY() + ")から半径" + radius);
    }
    public double getPerimeter(){   //半径を使い、以下の計算式で算出した結果を返す。
        return radius * 2 * Math.PI;
    }
}
