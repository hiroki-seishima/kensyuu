package com.example.moattravel2.entity;  //17-4

import java.sql.Timestamp;  //データベースのTIMESTAMP型カラム（日付＋時刻）を扱うためのJava標準SQLクラスをインポート
import jakarta.persistence.Column;  
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn; //JPAでの多対1関係を定義するアノテーション.多数のエンティティが1つの親エンティティに紐づく関係を定義
import jakarta.persistence.ManyToOne;  //JPAでの多対1関係を定義するアノテーション.外部キー列の名前を明示的に指定します。子テーブル側（Many側）に外部キー列を作成 今回は１ユーザに対し複数の予約ができるようにするのに使う
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "furigana")
    private String furigana;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password; 

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role; 

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)
    private Timestamp updatedAt;     
    
}
