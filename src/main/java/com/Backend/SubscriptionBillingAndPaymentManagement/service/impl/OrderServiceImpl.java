package com.Backend.SubscriptionBillingAndPaymentManagement.service.impl;

import com.Backend.SubscriptionBillingAndPaymentManagement.domain.Order;
import com.Backend.SubscriptionBillingAndPaymentManagement.domain.User;
import com.Backend.SubscriptionBillingAndPaymentManagement.enumeration.OrderStatus;
import com.Backend.SubscriptionBillingAndPaymentManagement.repository.OrderRepository;
import com.Backend.SubscriptionBillingAndPaymentManagement.repository.UserRepository;
import com.Backend.SubscriptionBillingAndPaymentManagement.service.EmailService;
import com.Backend.SubscriptionBillingAndPaymentManagement.service.OrderService;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;


@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository;
    private UserRepository userRepository;

    public OrderServiceImpl(OrderRepository orderRepository,
                            UserRepository userRepository
                            ) {
        this.orderRepository = orderRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Order> getAllOrders() {
        return this.orderRepository.findAll();
    }

    @Override
    public Order create(Order order, String email) throws MessagingException {
        order.setDateCreated(LocalDate.now());


        return this.orderRepository.save(order);
    }


    @Override
    public void update(Order order) {
        this.orderRepository.save(order);

    }

}
