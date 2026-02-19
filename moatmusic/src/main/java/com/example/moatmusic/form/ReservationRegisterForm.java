package com.example.moatmusic.form;
 import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReservationRegisterForm {
private Integer stringInstrumentId;

private Integer userId;
private String rentalStartTimeDate;
private String rentalEndTimeDate;
private Integer rentalQuantity;
private Integer amount;
}
