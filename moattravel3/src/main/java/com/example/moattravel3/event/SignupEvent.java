package com.example.moattravel3.event;  //メール認証機能のイベント

import org.springframework.context.ApplicationEvent;  //予約完了→メール送信、決済成功→ログ記録などの非同期処理をイベント駆動で実現

import com.example.moattravel3.entity.User;
import lombok.Getter;

@Getter
public class SignupEvent extends ApplicationEvent{
    private User user;
    private String requestUrl;

    public SignupEvent(Object source,User user,String requestUrl){
        super(source);  //親クラスのコンストラクタを呼出して、SignupEventのコンストラクタを作成
        this.user =user;
        this.requestUrl = requestUrl;
    }
    
}
