package com.example.moattravel3.service; //決済フローを自動化するコアサービズ

import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.example.moattravel3.form.ReservationRegisterForm;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Event;
import com.stripe.model.StripeObject;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import com.stripe.param.checkout.SessionRetrieveParams;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class StripeService {
    @Value("${stripe.api-key}") // 設定ファイル（application propertiesから呼び出し
    private String stripeApiKey;

    private final ReservationService reservationService;

    public StripeService(ReservationService reservationService) { // コンストラクタ
        this.reservationService = reservationService;
    }

    // セッションを作成し、Stripeに必要な情報を返す
    public String createStripeSession(String houseName, ReservationRegisterForm reservationRegisterForm,
            HttpServletRequest httpServletRequest) {
        stripe.apiKey = "シークレットキー";
        String requestUrl = new String(httpServletRequest.getRequestURL()); // 現在リクエストURLを取得(/houses/5/reservations/confirm)

        SessionCreateParams params = SessionCreateParams
                .builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD) // 支払はクレジットカードのみ
                .addLineItem(SessionCreateParams.LineItem
                        .builder() // LineItemはstripeの決済画面に表示される１行分の商品情報
                        .setPriceData(SessionCreateParams.LineItem.PriceData
                                .builder()
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData
                                        .builder()
                                        .setName(houseName)
                                        .build()) // buildで段階敵に構築（htmlの<div></div>とかをイメージ
                                .setUnitAmount((long) reservationRegisterForm.getAmount())
                                .setCurrency("jpy")
                                .build())
                        .setQuantity(1L)
                        .build())// 数量を１にしてbuildつけてLineItemの設定を完了
                .setMode(SessionCreateParams.Mode.PAYMENT) // 決済モードを通常決済のみ
                .setSuccessUrl(
                        requestUrl.replaceAll("/houses/[0-9]+/reservations/confirm", "") + "/reservations?reserved") // 決済成功時に/reservation?reservedにリダイレクト
                .setCancelUrl(requestUrl.replace("/reservations/confirm", "")) // 決済キャンセル時は１つ前のページにリダイレクト
                .setPaymentIntentData(SessionCreateParams.PaymentIntentData
                        .builder()
                        .putMetadata("houseId", reservationRegisterForm.getHouseId().toString()) // stripeの機能でメタデータ（決済情報に任意のデータをくっつけること）メタデータに予約情報データを保存
                        .putMetadata("userId", reservationRegisterForm.getUserId().toString())
                        .putMetadata("checkinDate", reservationRegisterForm.getCheckinDate())
                        .putMetadata("checkoutDate", reservationRegisterForm.getCheckoutDate())
                        .putMetadata("numberOfPeople", reservationRegisterForm.getNumberOfPeople().toString())
                        .putMetadata("amount", reservationRegisterForm.getAmount().toString())
                        .build())
                .build();

        try {
            Session session = Session.create(params); // stripe APIにセッション作成リクエスト送信
            return session.getId(); // 生成されたIDを返す
        } catch (StripeException e) {
            e.printStackTrace(); // エラー表示
            return "";
        }
    }

    // セッションから予約情報を取得し、ReservationServiceクラスを介してデータベースに登録する
    public void processSessionCompleted(Event event) {
        Optional<StripeObject> optionalStripeObject = event.getDataObjectDeserializer().getObject(); // webhookイベントからStripeオブジェクトを取得
        optionalStripeObject.ifPresent(stripeObject -> { // stripeオブジェクトが存在する場合のみ処理実行
            Session session = (Session) stripeObject; // sessionをキャスト化（この処理をしないとsession.が使えない）
            SessionRetrieveParams params = SessionRetrieveParams
                    .builder()
                    .addExpand("payment_intent")  //関連データを一緒に取得する
                    .build();

            try {
                session = Session.retrieve(session.getId(), params, null); // 最新のセッション情報をstripeから再取得
                Map<String, String> paymentIntentObject = session.getPaymentIntentObject().getMetadata(); // メタデータを取得
                reservationService.create(paymentIntentObject); // メタデータから予約の自動作成とDB保存
            } catch (StripeException e) {
                e.printStackTrace(); // エラー表示
            }
        });
    }
}
