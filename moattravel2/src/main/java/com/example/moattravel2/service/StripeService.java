package com.example.moattravel2.service; //37-4

import java.util.Map; //38-4で追加
import java.util.Optional; //38-4で追加

import org.springframework.beans.factory.annotation.Value; //37-5
import org.springframework.stereotype.Service;

import com.example.moattravel2.form.ReservationRegisterForm;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;

import com.stripe.model.Event; //38-4で追加
import com.stripe.model.StripeObject; //38-4で追加

import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionRetrieveParams;  //38-4で追加

import jakarta.servlet.http.HttpServletRequest;

@Service
public class StripeService {
    @Value("${stripe.api-key}") // 37-5
    private String stripeApiKey; // 37-5
    private final ReservationService reservationService;

    public StripeService(ReservationService reservationService) { // 38-4で追加
        this.reservationService = reservationService;
    }

    // セッションを作成し、Stripeに必要な情報を返す

    public String createStripeSession(String houseName, ReservationRegisterForm reservationRegisterForm,
            HttpServletRequest httpServletRequest) {
        Stripe.apiKey = "sk_test_51Sq26A6fHhDNGpxcbsutb62kHh89CBEobk8bf9oHH7Qh5nnM9uceQMKx7LSaUEZX3lEnVI3qbvkl73kljtC6ORpg00b6B8ezrz";
        String requestUrl = new String(httpServletRequest.getRequestURL());
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .addLineItem(
                        SessionCreateParams.LineItem.builder()
                                .setPriceData(
                                        SessionCreateParams.LineItem.PriceData.builder()
                                                .setProductData(
                                                        SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                .setName(houseName)
                                                                .build())
                                                .setUnitAmount((long) reservationRegisterForm.getAmount())
                                                .setCurrency("jpy")
                                                .build())
                                .setQuantity(1L)
                                .build())
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(
                        requestUrl.replaceAll("/houses/[0-9]+/reservations/confirm", "") + "/reservations?reserved")
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

    // セッションから予約情報を取得し、ReservationServiceクラスを介してデータベースに登録する　　38-4で追加
    public void processSessionCompleted(Event event) {
        Optional<StripeObject> optionalStripeObject = event.getDataObjectDeserializer().getObject();
        optionalStripeObject.ifPresent(stripeObject -> {
            Session session = (Session) stripeObject;
            SessionRetrieveParams params = SessionRetrieveParams.builder().addExpand("payment_intent").build();

            try {
                session = Session.retrieve(session.getId(), params, null);
                Map<String, String> paymentIntentObject = session.getPaymentIntentObject().getMetadata();
                reservationService.create(paymentIntentObject);
            } catch (StripeException e) {
                e.printStackTrace();
            }
        });
    }
}
