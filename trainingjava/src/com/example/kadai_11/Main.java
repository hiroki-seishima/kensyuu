package com.example.kadai_11;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
    
        System.out.print("0:円 2:線 3:三角形 4:長方形 44:正方形 のどれか値を入力");  //入力指示を出力
        Scanner sc = new Scanner(System.in);
        int i = sc.nextInt();  //キーボード入力
        Circle c = new Circle(100,100,20);   //円のインスタンス生成　引数を入力  
        Line l = new Line(0,0,100,100);     //線のインスタンス生成　引数を入力
        Triangle t = new Triangle(0,0,100,100,0,200);     //三角形のインスタンス生成　引数を入力
        Rectangle re = new Rectangle(0,0,100,50);     //長方形のインスタンス生成　引数を入力
        Square s = new Square(0,0,200);     //正方形のインスタンス生成　引数を入力

        if (i == 0) {    //円を選択した時
            System.out.println("図形描画[0:円 2:線 3:三角形 4:長方形 44:正方形]:>" + i);
            c.draw();  //描画
            double circlePerimeter = c.getPerimeter();   //変数.getPerimeter();だとvoidだと思われている。呼び出し元がdoubleなのでdouble型に変換が必要。変数.getInternalAngleも同様
            System.out.println(circlePerimeter);     //円の周長を出力
            
        }else if(i == 2){   //線を選択した時
            System.out.println("図形描画[0:円 2:線 3:三角形 4:長方形 44:正方形]:>" + i);
            l.draw();   //描画
            double linePerimeter = l.getPerimeter();
            System.out.println(linePerimeter);   //3点を足した線の長さ

        }else if(i == 3){   //三角形を選択した時
            System.out.println("図形描画[0:円 2:線 3:三角形 4:長方形 44:正方形]:>" + i);
            t.draw();   //描画
            double trianglePerimeter = t.getPerimeter();
            System.out.println(trianglePerimeter);   //三角形の3辺の合計の長さ
            int triangleInternalAngle = t.getInternalAngle();
            System.out.println(triangleInternalAngle);  //三角形の内角

        }else if(i == 4){   //長方形を選択した時
            System.out.println("図形描画[0:円 2:線 3:三角形 4:長方形 44:正方形]:>" + i);
            re.draw();   //描画
            double rectanglePerimeter = re.getPerimeter();
            System.out.println(rectanglePerimeter);  //長方形の4辺の合計の長さ
            int rectangleInternalAngle = re.getInternalAngle();
            System.out.println(rectangleInternalAngle);   //長方形の内角

        }else if(i == 44){   //正方形を選択した時
            System.out.println("図形描画[0:円 2:線 3:三角形 4:長方形 44:正方形]:>" + i);
            s.draw();   //描画
            double squarePerimeter = s.getPerimeter();
            System.out.println(squarePerimeter);   //正方形の4辺の合計の長さ
            int squareInternalAngle = s.getInternalAngle();
            System.out.println(squareInternalAngle);  //正方形の内角
        }else{
            System.out.println("");
        }
        sc.close();   //closeでscannerの対象範囲を閉じる
    }
}
