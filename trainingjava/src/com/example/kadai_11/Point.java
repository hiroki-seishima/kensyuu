package com.example.kadai_11;

public class Point {
    private int x;   //x座標を表すprivateフィールド
    private int y;   //y座標を表すprivateフィールド
    
    public Point(){   //引数なしコンストラクタの定義 x座標、y座標ともに0で初期化
        this.x = 0;
        this.y = 0;
    }
    public Point(int x,int y){   //x座標、y座標を受け取りその値で初期化するコンストラクタの定義。
    //第１引数で渡された値をxフィールドに代入、第２引数で渡された値をyフィールドに代入
        this.x = x;
        this.y = y;
    }
    public int getX(){   //xフィールド値を返すメゾッド
        return this.x;
    }

    public void setX(int x){   //引数で渡された値を、xフィールドにセットするメゾッド
        this.x = x;
    }

    public int getY(){   //yフィールドの値を返すメゾッド
        return this.y;
    }

    public void setY(int y){   //引数で渡された値を、yフィールドにセットするメゾッド
        this.y = y;
    }


}
