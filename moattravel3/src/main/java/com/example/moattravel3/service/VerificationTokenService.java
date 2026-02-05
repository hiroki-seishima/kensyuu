package com.example.moattravel3.service;  //メール認証用のサービスクラス

import org.springframework.stereotype.Service;  //サービス

import org.springframework.transaction.annotation.Transactional;  //トランザクション　成功すれば進み失敗すれば最初から
import com.example.moattravel3.entity.User;
import com.example.moattravel3.entity.VerificationToken;
import com.example.moattravel3.repository.VerificationTokenRepository;

@Service
public class VerificationTokenService {  
    private final VerificationTokenRepository verificationTokenRepository; 
    
    public VerificationTokenService(VerificationTokenRepository verificationTokenRepository){  //コンストラクタ
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Transactional
    public void create(User user,String token) {  //ユーザー登録時に生成した認証トークンをDBに保存
        VerificationToken verificationToken = new VerificationToken();  //トークンのインスタンス生成
        verificationToken.setUser(user);  //ユーザーをフィールドにセット
        verificationToken.setToken(token);  //トークンをフィールドにセット

        verificationTokenRepository.save(verificationToken);  //DB保存
    }

    //トークンの文字列で検索した結果を返す

    public VerificationToken getVerificationToken(String token) {
        return verificationTokenRepository.findByToken(token);  //トークンを検索して出てきたのを返す
    }
}
