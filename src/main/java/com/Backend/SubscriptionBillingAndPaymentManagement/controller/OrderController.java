package com.Backend.SubscriptionBillingAndPaymentManagement.controller;


import com.Backend.SubscriptionBillingAndPaymentManagement.domain.Order;
import com.Backend.SubscriptionBillingAndPaymentManagement.domain.OrderProduct;
import com.Backend.SubscriptionBillingAndPaymentManagement.dto.OrderProductDto;
import com.Backend.SubscriptionBillingAndPaymentManagement.enumeration.OrderStatus;
import com.Backend.SubscriptionBillingAndPaymentManagement.exception.domain.ResourceNotFoundException;
import com.Backend.SubscriptionBillingAndPaymentManagement.service.OrderProductService;
import com.Backend.SubscriptionBillingAndPaymentManagement.service.OrderService;
import com.Backend.SubscriptionBillingAndPaymentManagement.service.ProductService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

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

    public OrderController(ProductService productService, OrderService orderService, OrderProductService orderProductService) {
        this.productService = productService;
        this.orderService = orderService;
        this.orderProductService = orderProductService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public @NotNull List<Order> list(){
        return this.orderService.getAllOrders();
    }

    @PostMapping
    public ResponseEntity<Order> create (@RequestBody OrderForm form){
        List<OrderProductDto> formDtos = form.getProductOrders();
        validateProductExistance(formDtos);
        Order order = new Order();
        order.setStatus(OrderStatus.PAID.name());
        order = this.orderService.create(order);

        List<OrderProduct> orderProducts = new ArrayList<>();
        for (OrderProductDto orderProductDto : formDtos) {
            orderProducts.add(orderProductService.create(new OrderProduct(order, productService.findProductById(
                    orderProductDto.getProduct()
                            .getId()), orderProductDto.getQuantity())));
        }
        order.setOrderProducts(orderProducts);

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

