package com.example.moattravel3.event;  //イベントを発生させる

import org.springframework.context.ApplicationEventPublisher;  //予約完了→メール送信、決済成功→ログ記録などの非同期処理をイベント駆動で実現
import org.springframework.stereotype.Component;  //クラスをBeanに自動登録する


import com.example.moattravel3.entity.User;

@Component
public class SignupEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public SignupEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void publishSignupEvent(User user,String requestUrl) {  //イベントを発行するためのメソッド　引数は、１、新規登録したユーザー、２、イベント発生時のURL
        applicationEventPublisher.publishEvent(new SignupEvent(this,user,requestUrl));//イベントのオブジェクトを生成　このtきのthisは自身をさしており、SignupEventPiblisherクラスのインスタンス
    }
}
