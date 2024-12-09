package com.app.spendWise.controller;

import com.stripe.model.PaymentIntent;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @PostMapping("/create-payment-intent")
    public Map<String, String> createPaymentIntent() {
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("amount", 500); // $5.00 in cents
            params.put("currency", "usd");
            params.put("payment_method_types", List.of("card"));

            PaymentIntent paymentIntent = PaymentIntent.create(params);
            Map<String, String> response = new HashMap<>();

            response.put("clientSecret", paymentIntent.getClientSecret());
            return response;
        } catch (Exception e) {
            throw new RuntimeException("Error creating payment intent", e);
        }
    }
}
