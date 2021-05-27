package com.Backend.SubscriptionBillingAndPaymentManagement.repository;

import com.Backend.SubscriptionBillingAndPaymentManagement.domain.OrderProduct;
import com.Backend.SubscriptionBillingAndPaymentManagement.domain.OrderProductPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductPK> {
}
