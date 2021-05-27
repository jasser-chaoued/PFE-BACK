package com.Backend.SubscriptionBillingAndPaymentManagement.service.impl;

import com.Backend.SubscriptionBillingAndPaymentManagement.domain.Order;
import com.Backend.SubscriptionBillingAndPaymentManagement.repository.OrderRepository;
import com.Backend.SubscriptionBillingAndPaymentManagement.service.OrderService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;


@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> getAllOrders() {
        return this.orderRepository.findAll();
    }

    @Override
    public Order create(Order order) {
        order.setDateCreated(LocalDate.now());
        return this.orderRepository.save(order);
    }

    @Override
    public void update(Order order) {
        this.orderRepository.save(order);
    }
}
