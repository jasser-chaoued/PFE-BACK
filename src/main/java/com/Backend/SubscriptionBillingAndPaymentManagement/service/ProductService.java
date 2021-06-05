package com.Backend.SubscriptionBillingAndPaymentManagement.service;

import com.Backend.SubscriptionBillingAndPaymentManagement.domain.Product;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import java.util.List;

@Validated
public interface ProductService {

    Product addProduct(Product product);

    List<Product> getAllProducts();

    Product updateProduct(Product product);

    Product findProductById(long id);

    void deleteProduct(long id);
}
