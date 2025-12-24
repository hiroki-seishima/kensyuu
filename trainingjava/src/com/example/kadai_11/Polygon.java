package com.example.kadai_11;

public abstract class Polygon extends Shape{   //親クラスShapeクラスを継承
    int angle;    //内角（辺）の数を表すprotectedフィールド

    public abstract void draw();  //図形描画機能の定義

    public abstract double getPerimeter();   //長さ測定機能の定義


    public int getInternalAngle() {   //angleフィールドを使い、内角の和を算出する→戻り値を算出された内角の和(int型)

            return (angle-2) * 180;
        
    }

}
