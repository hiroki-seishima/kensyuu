package com.example.moattravel2.event; //23-5

import java.util.UUID;  //128ビットのグローバル一意識別子（Universally Unique Identifier）を扱うJava標準クラスをインポート

import org.springframework.context.event.EventListener;  //128ビットのグローバル一意識別子（Universally Unique Identifier）を扱うJava標準クラスをインポート
import org.springframework.mail.SimpleMailMessage;  //Spring Frameworkのシンプルなテキストメール送信クラスをインポート。メールの内容作成
import org.springframework.mail.javamail.JavaMailSender;  //Springのメール送信インターフェースをインポート
import org.springframework.stereotype.Component;  //クラスをSpringのBean（管理対象オブジェクト）として自動登録するアノテーションをインポート

import com.example.moattravel2.entity.User;
import com.example.moattravel2.service.VerificationTokenService;

@Component
public class SignupEventListener {
    private final VerificationTokenService verificationTokenService;

    private final JavaMailSender javaMailSender;

    public SignupEventListener(VerificationTokenService verificationTokenService, JavaMailSender mailSender) {
        this.verificationTokenService = verificationTokenService;
        this.javaMailSender = mailSender;
    }

    @EventListener
    private void onSignupEvent(SignupEvent signupEvent) {
        User user = signupEvent.getUser();
        String token = UUID.randomUUID().toString();
        verificationTokenService.create(user, token);

        String recipientAddress = user.getEmail();
        String subject = "メール認証";
        String confirmationUrl = signupEvent.getRequestUrl() + "/verify?token=" + token;
        String message = "以下のリンクをクリックして会員登録を完了してください。";

        SimpleMailMessage mailMessage = new SimpleMailMessage();  //内容作成（import org.springframework.mail.SimpleMailMessage;）
        mailMessage.setTo(recipientAddress);
        mailMessage.setSubject(subject);
        mailMessage.setText(message + "\n" + confirmationUrl);
        javaMailSender.send(mailMessage);  //送信実行（import org.springframework.mail.javamail.JavaMailSender;）
    }
}
