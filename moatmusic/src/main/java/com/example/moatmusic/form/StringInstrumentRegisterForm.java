package com.example.moatmusic.form;  //楽器登録フォーム

import org.springframework.web.multipart.MultipartFile;

 import jakarta.validation.constraints.Min;
 import jakarta.validation.constraints.NotBlank;
 import jakarta.validation.constraints.NotNull;
 import lombok.Data;

 @Data
public class StringInstrumentRegisterForm {
 @NotBlank(message = "楽器名を入力してください。")  //空白でエラーメッセージ
 private String name;

 private MultipartFile imageFile;

 @NotBlank(message = "説明を入力してください。")  //空白でエラーメッセージ
 private String description;

 @NotNull(message = "レンタル料金を入力してください。")  //数値以外でエラーメッセージ
 @Min(value = 1, message = "宿泊料金は1円以上に設定してください。")  //0以下でエラーメッセージ
 private Integer price;

 @NotNull(message = "最大貸出数を入力してください。")  //数値以外でエラーメッセージ
 @Min(value = 1, message = "最大貸出数は1台以上に設定してください。")  //0以下でエラーメッセージ
 private Integer capacity;

 @NotBlank(message = "利き手を入力してください。")  //空白でエラーメッセージ  右、左、両の選択のほうがいい？
 private String handedness;

}