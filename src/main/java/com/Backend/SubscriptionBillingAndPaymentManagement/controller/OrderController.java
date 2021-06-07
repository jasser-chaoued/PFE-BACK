package com.Backend.SubscriptionBillingAndPaymentManagement.controller;


import com.Backend.SubscriptionBillingAndPaymentManagement.domain.Order;
import com.Backend.SubscriptionBillingAndPaymentManagement.domain.OrderProduct;
import com.Backend.SubscriptionBillingAndPaymentManagement.domain.User;
import com.Backend.SubscriptionBillingAndPaymentManagement.dto.OrderProductDto;
import com.Backend.SubscriptionBillingAndPaymentManagement.enumeration.OrderStatus;
import com.Backend.SubscriptionBillingAndPaymentManagement.exception.domain.ResourceNotFoundException;
import com.Backend.SubscriptionBillingAndPaymentManagement.repository.OrderRepository;
import com.Backend.SubscriptionBillingAndPaymentManagement.repository.UserRepository;
import com.Backend.SubscriptionBillingAndPaymentManagement.service.EmailService;
import com.Backend.SubscriptionBillingAndPaymentManagement.service.OrderProductService;
import com.Backend.SubscriptionBillingAndPaymentManagement.service.OrderService;
import com.Backend.SubscriptionBillingAndPaymentManagement.service.ProductService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/orders")
public class OrderController {
    ProductService productService;
    OrderService orderService;
    OrderProductService orderProductService;
    OrderRepository orderRepository ;
    private EmailService emailService;
    private UserRepository userRepository;
    public long orderId ;

    public OrderController(ProductService productService, OrderService orderService, OrderProductService orderProductService, OrderRepository orderRepository, EmailService emailService, UserRepository userRepository) {
        this.productService = productService;
        this.orderService = orderService;
        this.orderProductService = orderProductService;
        this.orderRepository = orderRepository;
        this.emailService = emailService;
        this.userRepository = userRepository;

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public @NotNull List<Order> list(){
        return this.orderService.getAllOrders();
    }

    @PostMapping("/{email}")
    public ResponseEntity<Order> create (@RequestBody OrderForm form,
                                         @PathVariable("email") String email) throws MessagingException {
        List<OrderProductDto> formDtos = form.getProductOrders();
        validateProductExistance(formDtos);
        Order order = new Order();
        order.setUserEmail(email);
        order.setStatus(OrderStatus.PENDING.name());
        order = this.orderService.create(order, email);

        List<OrderProduct> orderProducts = new ArrayList<>();
        for (OrderProductDto orderProductDto : formDtos) {
            orderProducts.add(orderProductService.create(new OrderProduct(order, productService.findProductById(
                    orderProductDto.getProduct()
                            .getId()), orderProductDto.getQuantity())));
        }
        order.setOrderProducts(orderProducts);
        orderId = order.getId();
        this.orderService.update(order);

        String uri = ServletUriComponentsBuilder
                .fromCurrentServletMapping()
                .path("/order/{id}")
                .buildAndExpand(order.getId())
                .toString();
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", uri);
        return new ResponseEntity<>(order, headers, HttpStatus.CREATED);
    }


    @GetMapping("/cancel/{orderID}")
    @ResponseStatus(HttpStatus.OK)
    public void cancelOrder(@PathVariable long orderID)
    {
        Order order = this.orderRepository.findOrderById(orderID);
        order.setStatus(OrderStatus.CANCELLED.name());
        this.orderService.update( order);
    }
    @GetMapping("/pay/{orderID}")
    @ResponseStatus(HttpStatus.OK)
    public void payOrder(@PathVariable long orderID) throws MessagingException {
        Order order = this.orderRepository.findOrderById(orderID);
        order.setStatus(OrderStatus.PAID.name());
        System.out.println(order);
        User user = userRepository.findUserByEmail(order.getUserEmail());
        System.out.println(user);
        this.orderService.update( order);
        emailService.sendBillEmail(user, order);
    }


    private void validateProductExistance(List<OrderProductDto> orderProducts){
        List<OrderProductDto> list = orderProducts
                .stream()
                .filter(op -> Objects.isNull(productService.findProductById(op.getProduct().getId())))
                .collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(list)) {
            new ResourceNotFoundException("product not found");
        }

    }

    public static class OrderForm {
        private List<OrderProductDto> productOrders;

        public List<OrderProductDto> getProductOrders() {
            return productOrders;
        }

        public void setProductOrders(List<OrderProductDto> productOrders) {
            this.productOrders = productOrders;        }
    }
}

