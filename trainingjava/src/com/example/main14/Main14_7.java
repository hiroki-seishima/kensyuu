package com.example.main14;

public class Main14_7 {
    String name;
    int hp;


    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o instanceof Main14_4_5h){
            Main14_4_5h h = (Main14_4_5h)o;
            if (this.name.equals(h.name)){
                return true;
            }
        }
        return false;
    }
}
