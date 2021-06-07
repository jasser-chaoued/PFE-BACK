package com.Backend.SubscriptionBillingAndPaymentManagement.service;

import com.Backend.SubscriptionBillingAndPaymentManagement.domain.Order;
import org.springframework.validation.annotation.Validated;

import javax.mail.MessagingException;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
public interface OrderService {

    @NotNull List<Order> getAllOrders();

    Order create(@NotNull(message = "The Order cannot be null") @Valid Order order, String email) throws MessagingException;


    void update(@NotNull(message = "The Order cannot be null") @Valid Order order);
}
