package com.example.moattravel2.form;  //12-3
import org.springframework.web.multipart.MultipartFile;  //ファイルアップロード機能を利用するためのインターフェース
import jakarta.validation.constraints.Min;  //Jakarta Bean Validation（Bean Validation 3.0以降）のアノテーション。数値フィールドに最小値を設定
import jakarta.validation.constraints.NotBlank;  //Jakarta Bean Validationの文字列用制約アノテーション 。文字列が null でなく、空文字でも空白文字のみ（” “、”\t”など）でもないことを検証
import jakarta.validation.constraints.NotNull;  //Jakarta Bean Validationの制約アノテーションで、オブジェクトがnullでないことを検証。任意の型のフィールド、メソッド引数、戻り値に適用され、null値を拒否します。文字列の場合は @NotBlank と異なり、空文字や空白文字は許可されます
import lombok.Data;  //Project Lombokライブラリのアノテーションをインポートし、Javaクラスの定型コード（getter/setter/toString/equals/hashCode）を自動生成
@Data
public class HouseRegisterForm {
    @NotBlank(message = "民宿名を入力してください。")
    private String name;
    private MultipartFile imageFile;

    @NotBlank(message = "説明を入力してください。")
    private String description;   

    @NotNull(message = "宿泊料金を入力してください。")

    @Min(value = 1, message = "宿泊料金は1円以上に設定してください。")
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
