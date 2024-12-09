package com.app.spendWise.config;

import com.stripe.Stripe;
import jakarta.annotation.PostConstruct;

public class StripeConfig {
    @PostConstruct
    public void init() {
        Stripe.apiKey = "sk_test_51QU2KtG16wBk4x4NCnD12b95T7dRhgwMxsd9D0Gg5ftAraDEY28ryjLovsPhBmB2NE1icBEsf9y4xIQJwYqPDLH400UWEs8cCu";
    }
}
