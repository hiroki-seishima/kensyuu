package com.example.moatmusic.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "string_instrument")  //string_instrument
@Data

public class StringInstrument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")   //id
    private Integer id;

    @Column(name = "name")
    private String name;  //楽器名

    @Column(name = "image_name")
    private String imageName;  //楽器画像のファイル名

    @Column(name = "description")
    private String description;  //楽器の説明

    @Column(name = "price")
    private Integer price;  //1時間あたりのレンタル料金

    @Column(name = "capacity")
    private Integer capacity;  //最大貸出数

    @Column(name = "created_at", insertable = false, updatable = false)  //作成日時
    private Timestamp createdAt;

    @Column(name = "updated_at", insertable = false, updatable = false)  //更新日時
    private Timestamp updatedAt;
}