package com.example.moattravel2.form; //21-2　フォームの作成

import org.hibernate.validator.constraints.Length;  //Hibernate Validatorが提供する文字列の長さチェック用アノテーション@Lengthを使うためのインポート

import jakarta.validation.constraints.Email;  //Bean Validation標準のメールアドレス形式チェックアノテーションをインポート
import jakarta.validation.constraints.NotBlank;//空文字・空文字以外をエラーチェックするためのアノテーションをインポート
import lombok.Data;

@Data
public class SignupForm {
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
    @Email(message = "メールアドレスは正しい形式で入力してください。")
    private String email;

    @NotBlank(message = "パスワードを入力してください。")
    @Length(min = 8, message = "パスワードは8文字以上で入力してください。") //String型を対象に文字数の最小値・最大値を制約できる　min=8なので最低８文字
    private String password;

    @NotBlank(message = "パスワード（確認用）を入力してください。")
    private String passwordConfirmation;
}
