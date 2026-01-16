package com.example.moattravel.service;

import java.util.Map;  //38章で追加
import java.util.Optional;  //38章で追加

import org.springframework.beans.factory.annotation.Value; 
import org.springframework.stereotype.Service;

import com.example.moattravel.form.ReservationRegisterForm;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;  //38章で追加
import com.stripe.model.StripeObject;  //38章で追加
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionRetrieveParams;  //38章で追加

import jakarta.servlet.http.HttpServletRequest;

@Service
public class StripeService {
    @Value("${stripe.api-key}")
    private String stripeApiKey;
    // セッションを作成し、Stripeに必要な情報を返す
    private final ReservationService reservationService;  //38章で追加

    public StripeService(ReservationService reservationService) {  //38章で追加
        this.reservationService = reservationService;    //38章で追加
    }    

    // セッションを作成し、Stripeに必要な情報を返す  
    public String createStripeSession(String houseName, ReservationRegisterForm reservationRegisterForm, HttpServletRequest httpServletRequest) {
        Stripe.apiKey = stripeApiKey;
        String requestUrl = new String(httpServletRequest.getRequestURL());
        SessionCreateParams params =
        SessionCreateParams.builder()
        .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
        .addLineItem(
            SessionCreateParams.LineItem.builder()
            .setPriceData(
                SessionCreateParams.LineItem.PriceData.builder()   
                .setProductData(
                    SessionCreateParams.LineItem.PriceData.ProductData.builder()
                    .setName(houseName)
                    .build())
                    .setUnitAmount((long)reservationRegisterForm.getAmount())
                    .setCurrency("jpy")                                
                    .build())
                    .setQuantity(1L)
                    .build())
                    .setMode(SessionCreateParams.Mode.PAYMENT)
                    .setSuccessUrl(requestUrl.replaceAll("/houses/[0-9]+/reservations/confirm", "") + "/reservations?reserved")
                    .setCancelUrl(requestUrl.replace("/reservations/confirm", ""))
                    .setPaymentIntentData(
                        SessionCreateParams.PaymentIntentData.builder()
                        .putMetadata("houseId", reservationRegisterForm.getHouseId().toString())
                        .putMetadata("userId", reservationRegisterForm.getUserId().toString())
                        .putMetadata("checkinDate", reservationRegisterForm.getCheckinDate())
                        .putMetadata("checkoutDate", reservationRegisterForm.getCheckoutDate())
                        .putMetadata("numberOfPeople", reservationRegisterForm.getNumberOfPeople().toString())
                        .putMetadata("amount", reservationRegisterForm.getAmount().toString())
                        .build())
                        .build();
                        try {
                            Session session = Session.create(params);
                            return session.getId();
                        } catch (StripeException e) {
                            e.printStackTrace();
                            return "";
                        }

    }
    // セッションから予約情報を取得し、ReservationServiceクラスを介してデータベースに登録する  38章で追加
    public void processSessionCompleted(Event event) {   //38章で追加
        Optional<StripeObject> optionalStripeObject = event.getDataObjectDeserializer().getObject();  //38章で追加
        optionalStripeObject.ifPresent(stripeObject -> {  //38章で追加
            Session session = (Session)stripeObject;  //38章で追加
            SessionRetrieveParams params = SessionRetrieveParams.builder().addExpand("payment_intent").build();  //38章で追加
            
            try {  //38章で追加
                session = Session.retrieve(session.getId(), params, null);  //38章で追加
                Map<String, String> paymentIntentObject = session.getPaymentIntentObject().getMetadata();  //38章で追加
                reservationService.create(paymentIntentObject);  //38章で追加
            } catch (StripeException e) {  //38章で追加
                e.printStackTrace();  //38章で追加
            }
        });
    }
}
