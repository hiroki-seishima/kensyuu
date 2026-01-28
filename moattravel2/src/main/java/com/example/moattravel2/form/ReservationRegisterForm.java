package com.example.moattravel2.form;  //34-3

import lombok.AllArgsConstructor;  //Lombokアノテーションで全フィールドを引数に持つコンストラクタを自動生成するためのインポート
import lombok.Data;

@Data
@AllArgsConstructor  //クラスに付けるだけで、全フィールドを初期化するコンストラクタが自動生成
public class ReservationRegisterForm {
    private Integer houseId;

    private Integer userId;

    private String checkinDate;

    private String checkoutDate;

    private Integer numberOfPeople;

    private Integer amount;

}
