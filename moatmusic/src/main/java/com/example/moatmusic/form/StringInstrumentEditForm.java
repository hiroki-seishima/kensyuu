package com.example.moatmusic.form;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StringInstrumentEditForm {
    @NotNull
    private Integer id;

    @NotBlank(message = "楽器名を入力してください。")
    private String name;

    private MultipartFile imageFile;

    @NotBlank(message = "説明を入力してください。")
    private String description;

    @NotNull(message = "レンタル料金を入力してください。")
    @Min(value = 1, message = "レンタル料金は1円以上に設定してください。")
    private Integer price;

    @NotNull(message = "最大貸出数を入力してください。")
    @Min(value = 1, message = "最大貸出数は1人以上に設定してください。")
    private Integer capacity;

    @NotBlank(message = "利き手を入力してください。")
    private String handedness;

}
