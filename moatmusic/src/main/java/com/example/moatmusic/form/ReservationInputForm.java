package com.example.moatmusic.form; //予約登録フォーム

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservationInputForm {

    @NotBlank(message = "レンタル期間を選択してください。")
    private String fromRentalStartTimeDateToRentalEndTimeDate;

    @NotNull(message = "レンタル台数を入力してください。")
    @Min(value = 1, message = "台数は1台以上に設定してください。")
    private Integer rentalQuantity;

    // レンタル開始日時を取得する
    public LocalDateTime getRentalStartTimeDate() {
        String[] rentalStartTimeDateAndRentalEndTimeDate = getFromRentalStartTimeDateToRentalEndTimeDate()
                .split(" から ");
        return LocalDateTime.parse(rentalStartTimeDateAndRentalEndTimeDate[0]);
    }

    // レンタル終了日時を取得する
    public LocalDateTime getRentalEndTimeDate() {
        String[] rentalStartTimeDateAndRentalEndTimeDate = getFromRentalStartTimeDateToRentalEndTimeDate()
                .split(" から ");
        return LocalDateTime.parse(rentalStartTimeDateAndRentalEndTimeDate[1]);
    }
}
