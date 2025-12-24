package com.example.kadai_11;

public class Rectangle extends Polygon{   //親クラスPolygonクラスを継承
    protected Point p;   //長方形の基準の位置を表すPoint型protectedフィールド
    protected int width;  //長方形の横幅を表すprotectedフィールド
    protected int height;   //長方形の縦幅を表すprotectedフィールド

    public Rectangle(int x,int y,int width,int height){   //第１引数と第２引数で受け取ったデータを用いて、１つのPointオブジェクトを生成し、pフィールドに代入する。
    // 第３引数と第４引数をそれぞれwidthフィールド、heightフィールドに代入する。スーパークラス内で定義されているangleフィールドに４を代入する。
        this.p = new Point(x,y);   //フィールド内のpに代入するためthis
        this.width = width;   //クラス内で設定しているのでthis
        this.height = height;   //クラス内で設定しているのでthis
        this.angle = 4;   //親クラスのフィールドから継承されているためthis 
    }
    public void draw() {
        System.out.println("[長方形(矩形)を描画]点(" + p.getX() + "," + p.getY() + ")を基準として幅" + width + "、高さ" + height + "の長方形");
    }
    public double getPerimeter(){   //横幅と縦幅を使い、以下の計算式で算出した結果を返す。
        return (width + height) * 2;   //正方形の辺の長さ
    }
}
