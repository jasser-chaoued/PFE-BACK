package com.Backend.SubscriptionBillingAndPaymentManagement.service.impl;

import com.Backend.SubscriptionBillingAndPaymentManagement.domain.OrderProduct;
import com.Backend.SubscriptionBillingAndPaymentManagement.repository.OrderProductRepository;
import com.Backend.SubscriptionBillingAndPaymentManagement.service.OrderProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderProductServiceImpl implements OrderProductService {

    private OrderProductRepository orderProductRepository;

    public OrderProductServiceImpl(OrderProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }

    @Override
    public OrderProduct create(OrderProduct orderProduct) {
        return this.orderProductRepository.save(orderProduct);
    }
}
