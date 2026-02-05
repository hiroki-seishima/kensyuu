package com.example.moattravel3.event;  //signupEventクラスから通知を受け、メール認証用メールを送信するためのクラス

import java.util.UUID;

import org.springframework.context.event.EventListener;  //イベントリスナー
import org.springframework.mail.SimpleMailMessage;  //送信メールの内容を作成
import org.springframework.mail.javamail.JavaMailSender;  //作成したメールを送信
import org.springframework.stereotype.Component;  //クラスをBeanに自動登録する

import com.example.moattravel3.entity.User;  
import com.example.moattravel3.service.VerificationTokenService;

@Component
public class SignupEventListener {
    private final VerificationTokenService verificationTokenService;
    private final JavaMailSender javaMailSender;

    public SignupEventListener(VerificationTokenService verificationTokenService, JavaMailSender javaMailSender){  //コンストラクタ
        this.verificationTokenService = verificationTokenService;
        this.javaMailSender = javaMailSender;
    }

    @EventListener
    private void onSignupEvent (SignupEvent signupEvent){
        User user = signupEvent.getUser();  //イベントからユーザーを取得
        String token = UUID.randomUUID().toString();  //認証トークンを生成
        verificationTokenService.create(user,token);  //生成したトークンをDBへ保存
        String recipientAddress = user.getEmail();  //送信メールを作成
        String subject = "メール認証";  //メールの件名をメール認証にする
        String confirmationUrl = signupEvent.getRequestUrl() + "/verify?token=" + token; //認証用URLの作成（文字のみ。URLのリンク機能はメールクライアントが行う）
        String message = "以下のリンクをクリックして会員登録を完了してください。"; //メール本文作成

        SimpleMailMessage mailMessage = new SimpleMailMessage();  //メールオブジェクト作成
        mailMessage.setTo(recipientAddress);  //宛先設定
        mailMessage.setSubject(subject);  //件名設定
        mailMessage.setText(message + "\n" + confirmationUrl);  //本文＋認証用URLの設定
        javaMailSender.send(mailMessage);  //メール送信実行
    }
    
}
