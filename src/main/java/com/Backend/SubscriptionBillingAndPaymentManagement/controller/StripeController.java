package com.Backend.SubscriptionBillingAndPaymentManagement.controller;

import com.Backend.SubscriptionBillingAndPaymentManagement.domain.Checkout;
import com.Backend.SubscriptionBillingAndPaymentManagement.domain.CheckoutPayment;
import com.google.gson.Gson;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/checkout")
public class StripeController {

    private static Gson gson = new Gson();

    @PostMapping("/payment")
    public String paymentCheckoutPage(@RequestBody CheckoutPayment payment) throws StripeException {
        init();
        System.out.println(payment.getAmount());
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT).setSuccessUrl(payment.getSuccessUrl())
                .setCancelUrl(payment.getCancelUrl())
                .addLineItem(
                        SessionCreateParams.LineItem.builder().setQuantity(payment.getQuantity())
                        .setPriceData(
                                SessionCreateParams.LineItem.PriceData.builder()
                                .setCurrency(payment.getCurrency()).setUnitAmount(payment.getAmount())
                                .setProductData(SessionCreateParams.LineItem.PriceData.ProductData.builder().setName(payment.getName()).build())
                                .build())
                                .build())
                .build();

        Session session = Session.create(params);
        Map<String, String> responseData = new HashMap<>();
        responseData.put("id", session.getId());
        return gson.toJson(responseData);
    }

    @PostMapping("/subscription")
    public String subscriptionWithCheckoutPage(@RequestBody Checkout checkout) throws StripeException{
        SessionCreateParams params = new SessionCreateParams.Builder().setSuccessUrl(checkout.getSuccessUrl()).setCancelUrl(checkout.getCancelUrl())
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.SUBSCRIPTION).addLineItem(new SessionCreateParams.LineItem.Builder().setQuantity(1L).setPrice(checkout.getPriceId()).build())
                .build();
        try {
            Session session = Session.create(params);
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("sessionId", session.getId());
            return gson.toJson(responseData);
        } catch (Exception e) {
            Map<String, Object> messageData = new HashMap<>();
            messageData.put("message", e.getMessage());
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("error", messageData);
            return gson.toJson(responseData);
        }
    }


    private static void init() {
        Stripe.apiKey="sk_test_51IuQ3bEWWeT3KBsDKN7cnaGRqmwz2Zn83hzFJ4o869EOkMldWVzTeK42EAhMx1nJrOa9LZVc36K8r4gLP0q7ru3700wgL9hdU0";
    }
}
