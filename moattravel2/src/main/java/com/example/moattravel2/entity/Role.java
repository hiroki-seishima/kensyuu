package com.example.moattravel2.entity;  //17-4
import jakarta.persistence.Column;  //Jakarta Persistence（旧JPA）の @Column アノテーション を使うためのインポート
import jakarta.persistence.Entity;  //Jakarta Persistence API (旧JPA) の Entity アノテーションをインポート
import jakarta.persistence.GeneratedValue;  //主キーの値を自動生成するためのJakarta Persistence APIアノテーションをインポート
import jakarta.persistence.GenerationType;  //@GeneratedValue アノテーションで使用する主キー生成戦略を定義するenum型をインポート
import jakarta.persistence.Id; // エンティティクラスの主キー（Primary Key）を指定するJakarta Persistence APIのアノテーションをインポート
import jakarta.persistence.Table;  //JPAエンティティが対応するデータベーステーブルの詳細を指定するアノテーションをインポート
import lombok.Data; 

@Entity //Entityクラス
@Table(name = "roles")
@Data
public class Role {

    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "id")
    private Integer id;
    
    @Column(name = "name")
    private String name;
}
