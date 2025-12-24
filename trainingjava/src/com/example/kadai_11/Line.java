package com.example.kadai_11;

public class Line implements Figure{
    private Point p1;   //線の始点を表すPoint型privateフィールド Point型はPointクラスでコンストラクタされている。
    private Point p2;   //線の終点を表すPoint型privateフィールド Point型はPointクラスでコンストラクタされている。
    public Line() {   //引数なしコンストラクタの定義 p1(x,y座標)、p2(x,y座標)全て０で初期化する。
        this.p1 = new Point(0,0);   //始点
        this.p2 = new Point(0,0);   //終点
    }
    public Line(int x1,int y1,int x2,int y2){  //引数で受け取ったデータを用いて、２つのPointオブジェクトを生成 p1フィールドとp2フィールドにそれぞれを代入する。
        this.p1 = new Point(x1,y1);   //始点
        this.p2 = new Point(x2,y2);   //終点
    }

    public void draw() {
        System.out.println("[線を描画]始点(" + p1.getX()+ "," + p1.getY() + ")から終点(" + p2.getX() + "," + p2.getY() + ")まで"); //Pointクラスでprivateになっているため
    }
    public double getPerimeter(){   //始点データと終点データを使い、以下の計算式で算出した結果を返す。
        double powResult1 = Math.pow((p2.getX()-p1.getX()),2);
        double powResult2 = Math.pow((p2.getY()-p1.getY()),2);
        return Math.sqrt(powResult1+powResult2);   //sqrtはdouble型のみしか受付ないため。
    }
    
}
