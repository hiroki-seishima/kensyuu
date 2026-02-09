package com.example.moattravel3.form;  //会員情報の編集フォーム

import jakarta.validation.constraints.NotBlank;  //空白エラー検出
import jakarta.validation.constraints.NotNull;  //nullエラー検出
import lombok.AllArgsConstructor;  //コンストラクタ自動生成
import lombok.Data;  //getter,setter等の自動生成

@Data
@AllArgsConstructor
public class UserEditForm {
    @NotNull
    private Integer id;

    @NotBlank(message = "氏名を入力してください。")
    private String name;

    @NotBlank(message = "フリガナを入力してください。")
    private String furigana;

    @NotBlank(message = "郵便番号を入力してください。")
    private String postalCode;

    @NotBlank(message = "住所を入力してください。")
    private String address;

    @NotBlank(message = "電話番号を入力してください。")
    private String phoneNumber; 

    @NotBlank(message = "メールアドレスを入力してください。")
    private String email;


}
