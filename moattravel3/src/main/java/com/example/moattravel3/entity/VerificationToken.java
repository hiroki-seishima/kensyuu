package com.example.moattravel3.entity;  //verificationTokenテーブルと紐づけるためのエンティティ

import java.sql.Timestamp;

import jakarta.persistence.Column;  //カラム
import jakarta.persistence.Entity;   //エンティティ
import jakarta.persistence.GeneratedValue;  //主キー（ID）をデータベース側で自動生成
import jakarta.persistence.GenerationType;  //@GeneratedValue で使用する主キー自動生成戦略を定義するenum型をインポート
import jakarta.persistence.Id; //主キー（Primary Key）を指定するフィールドをマークする
import jakarta.persistence.JoinColumn; //外部キー（Foreign Key）列を明示的に指定するため
import jakarta.persistence.OneToOne;  //1対1の関係性を定義
import jakarta.persistence.Table;  //テーブル
import lombok.Data;  //ゲッターやセッターメソッドを自動生成

@Entity
@Table(name = "verification_tokens")
@Data
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
     
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "token")
    private String token;

    @Column(name = "created_at",insertable = false,updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at",insertable = false,updatable = false)
    private Timestamp updatedAt;

}
