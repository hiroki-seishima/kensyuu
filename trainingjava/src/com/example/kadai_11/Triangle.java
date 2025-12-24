package com.example.kadai_11;

public class Triangle extends Polygon{   //親クラスPolygonクラスを継承
    private Point p1;   //三角形の点１を表すPoint型privateフィールド
    private Point p2;   //三角形の点２を表すPoint型privateフィールド
    private Point p3;   //三角形の点３を表すPoint型privateフィールド

    public Triangle(int x1,int y1,int x2,int y2,int x3,int y3){   //引数で受け取ったデータを用いて、３つのPointオブジェクトを生成
        this.p1 = new Point(x1,y1);
        this.p2 = new Point(x2,y2);
        this.p3 = new Point(x3,y3);
        this.angle = 3; //スーパークラス内で定義されているangleフィールドに3を代入

    }

    public void draw(){
        System.out.println("[三角形を描画]点１(" + p1.getX() + "," + p1.getY() + ")から点２(" + p2.getX() + "," + p2.getY() + ")、点３(" + p3.getX() + "," + p3.getY() + ")の三角形");
    }

    public double getPerimeter(){   //３つの座標を使い、以下の計算式で算出した結果を返す。
        double powResult1_1 = Math.pow((p2.getX()-p1.getX()),2);   //p2-p1のx座標を2乗した計算
        double powResult1_2 = Math.pow((p2.getY()-p1.getY()),2);   //p2-p1のy座標を2乗した計算
        double powResult2_1 = Math.pow((p3.getX()-p2.getX()),2);   //p3-p2のx座標を2乗した計算
        double powResult2_2 = Math.pow((p3.getY()-p2.getY()),2);   //p3-p2のy座標を2乗した計算
        double powResult3_1 = Math.pow((p1.getX()-p3.getX()),2);   //p1-p3のx座標を2乗した計算
        double powResult3_2 = Math.pow((p1.getY()-p3.getY()),2);   //p1-p3のy座標を2乗した計算
        return Math.sqrt(powResult1_1+powResult1_2) + Math.sqrt(powResult2_1+powResult2_2) + Math.sqrt(powResult3_1+powResult3_2);  //Lineクラスの点間での距離計算を参照
    } 
}
