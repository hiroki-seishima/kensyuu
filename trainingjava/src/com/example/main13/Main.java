package com.example.main13;
//13章を動かすのに使用(mainがなかったため)
public class Main {
    public static void main(String[] args) {
        Main13_1_4_5_6_8 h = new Main13_1_4_5_6_8();
        // h.hp = 100; //13_4でprivateしたときのエラー内容を確認
        Main13_3_7_9 k = new Main13_3_7_9();
        h.setName("あああ");  
        k.talk(h);   // h.setNameとk.talkの順番を逆にしたらsetNameで変更したものが反映されないため注意
    }
}
