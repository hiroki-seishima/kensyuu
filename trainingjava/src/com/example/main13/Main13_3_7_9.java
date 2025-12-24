package com.example.main13;
// 王様クラス
public class Main13_3_7_9 {
    void talk(Main13_1_4_5_6_8 h){
        System.out.println("王様：ようこそ我が国へ、勇者" + h.getName() + "よ。");   //ヒーロークラスでゲットメゾットを作成したことによりアクセス可
        System.out.println("王様：長旅疲れたであろう。");
        System.out.println("王様：まずは城下町を見てくるとよい。ではまた会おう。");
        // h.die();  // byeとdieを間違えたらしい。
    
    
    }
}
//勇者が死んでしまうためコメント表示とする