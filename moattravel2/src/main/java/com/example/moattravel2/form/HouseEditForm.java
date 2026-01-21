package com.example.moattravel2.form;  //14-3

import org.springframework.web.multipart.MultipartFile;  //Springでアップロードされたファイルを扱うためのインターフェース

import jakarta.validation.constraints.Min;  //数値フィールドに「最小値制限」をかけるBean Validationアノテーション.
import jakarta.validation.constraints.NotBlank;  //文字列が「空っぽじゃない」ことを保証するBean Validationアノテーション
import jakarta.validation.constraints.NotNull;  //オブジェクト・フィールドが null でないことを保証するBean Validationアノテーション.
import lombok.AllArgsConstructor;  //Lombokが全フィールド引数付きのコンストラクタを自動生成するアノテーション
import lombok.Data;  //@Dataクラス内の全フィールドの中の静的インスタンスフィールドを対象に自動生成を行う。

@Data
@AllArgsConstructor
public class HouseEditForm {
    @NotNull    //null値を拒否しますが、空文字や空白はOKです。日付、オブジェクト型、数値型に適している
    private Integer id;

    @NotBlank(message = "民宿名を入力してください。")  //文字を入力しているかどうかを検証して、空白、空文字、nullはエラーとなるようにする
    private String name;
    private MultipartFile imageFile;

    @NotBlank(message = "説明を入力してください。")  
    private String description;

    @NotNull(message = "宿泊料金を入力してください。")  

    @Min(value = 1, message = "宿泊料金は1円以上に設定してください。")  //@Minで指定した値以上でなければ検証エラーになるようにする
    private Integer price;

    @NotNull(message = "定員を入力してください。")  

    @Min(value = 1, message = "定員は1人以上に設定してください。")  
    private Integer capacity;

    @NotBlank(message = "郵便番号を入力してください。")
    private String postalCode;

    @NotBlank(message = "住所を入力してください。")
    private String address;

    @NotBlank(message = "電話番号を入力してください。")
    private String phoneNumber;
}
