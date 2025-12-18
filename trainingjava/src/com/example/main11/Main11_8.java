package com.example.main11;

public abstract class Main11_8 {
    String name;
    int hp;
    
    public void run(){
        System.out.println(this.name + "は逃げ出した");
    }

    public abstract void attack(Matango m);
}
