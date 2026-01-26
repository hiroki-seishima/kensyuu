package com.example.moattravel2.entity;  //23-4

import java.sql.Timestamp;  //データベースのTIMESTAMP/DATETIME型の日時データを扱うためのJava標準クラスをインポート

import jakarta.persistence.Column;  //JPAエンティティのフィールドをデータベースの特定カラムにマッピングするためのアノテーションをインポート
import jakarta.persistence.Entity;  //JPAで通常のJavaクラスをデータベーステーブルに対応する永続化エンティティ（Entity）に変換するためのアノテーションをインポート
import jakarta.persistence.GeneratedValue;  //JPAエンティティの主キー（ID）をデータベース側で自動生成するためのアノテーションをインポート
import jakarta.persistence.GenerationType;  // @GeneratedValue で使用する主キー自動生成戦略を定義するenum型をインポート
import jakarta.persistence.Id;  //JPAエンティティの主キー（Primary Key）を指定するフィールドをマークするアノテーションをインポート
import jakarta.persistence.JoinColumn;  //JPAで外部キー（Foreign Key）列を明示的に指定するためのアノテーションをインポート
import jakarta.persistence.OneToOne;  //JPAで1対1の関係性を定義するアノテーションをインポート
import jakarta.persistence.Table;  //JPAエンティティが対応するデータベーステーブルの名前や設定を指定するためのアノテーションをインポート
import lombok.Data;  //Lombokライブラリのアノテーションで、定型コード（getter/setter/constructor等）を自動生成

@Entity
@Table(name = "verification_tokens")
@Data

public class VerificationToken {
    @Id //エンティティクラス内で@Idを付けたフィールドが、データベーステーブルの主キー列に対応。１つのエンティティには必ず１つはいる
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")  //エンティティクラスのフィールド（例： private Integer id; ）がMySQLのどのカラムに対応するかを指定します。省略するとフィールド名と同じ名前のカラムに自動マッピングされますがColumnで名前や制約をカスタマイズできます。
    private Integer id;

    @OneToOne  //2つのエンティティ間で1:1の関連を表現します。1つのレコードがもう1つのエンティティの1つのインスタンスとだけ関連付けられます。
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "token")
    private String token;

    @Column(name = "created_at", insertable = false, updatable = false)  // MySQLの created_at カラムにマッピング（対応させる、紐付け）、INSERT時にこのフィールドの値をSQLに含めない、UPDATE時にこのフィールドの値をSQLに含めない
    private Timestamp createdAt;   //TimestampはSQLデータベース（MySQLなど）と連携する際に、作成日時（created_at）や予約日時を正確に保存・取得するためのクラス

    @Column(name = "updated_at", insertable = false, updatable = false)
    private Timestamp updatedAt;
}
