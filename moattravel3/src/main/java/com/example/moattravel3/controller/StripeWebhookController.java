package com.example.moattravel3.controller;  //stripeから送られてくるwebhookを受け取り、予約完了をするコントローラー

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.example.moattravel3.service.StripeService;
import com.stripe.Stripe;
import com.stripe.exception.SignatureVerificationException;
import com.stripe.model.Event;
import com.stripe.net.Webhook;

@Controller
public class StripeWebhookController {
    private final StripeService stripeService;

    @Value("${stripe.api-key}")  
    private String stripeApiKey;

    @Value("${stripe.webhook-secret}")
    private String webhookSecret;

    public StripeWebhookController(StripeService stripeService) { //コンストラクタ
        this.stripeService = stripeService;
    }

    @PostMapping("/stripe/webhook")
    public ResponseEntity<String> webhook(@RequestBody String payload,@RequestHeader("Stripe-Signature")String sigHeader) {
        Stripe.apiKey = stripeApiKey;  //stripeにAPIキーをわたす
        Event event = null;  //stripeから送られるイベント情報をいれる箱（最初は空）

        try { 
            event = Webhook.constructEvent(payload,sigHeader,webhookSecret);  //署名検証
        } catch (SignatureVerificationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);  //署名が偽物であったらエラーを出す
        }
        if ("checkout.session.completed".equals(event.getType())){//支払完了であれば
            stripeService.processSessionCompleted(event);  //支払完了通知をstripeServiceに処理依頼

            
        }
        return new ResponseEntity<>("Success",HttpStatus.OK); //正常に受け取ったことを返す
    }
}

