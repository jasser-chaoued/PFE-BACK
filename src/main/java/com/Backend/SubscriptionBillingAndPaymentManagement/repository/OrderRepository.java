package com.Backend.SubscriptionBillingAndPaymentManagement.repository;

import com.Backend.SubscriptionBillingAndPaymentManagement.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Order findOrderById(Long id);
}
