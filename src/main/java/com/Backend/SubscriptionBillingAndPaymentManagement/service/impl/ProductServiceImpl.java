package com.Backend.SubscriptionBillingAndPaymentManagement.service.impl;

import com.Backend.SubscriptionBillingAndPaymentManagement.domain.Product;
import com.Backend.SubscriptionBillingAndPaymentManagement.exception.domain.ProductNotFoundException;
import com.Backend.SubscriptionBillingAndPaymentManagement.repository.ProductRepository;
import com.Backend.SubscriptionBillingAndPaymentManagement.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;


@Service
@Transactional
public class ProductServiceImpl implements ProductService {


    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public Product addProduct(Product product) {
        product.setProductCode(UUID.randomUUID().toString());
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product findProductById(long id) {
        return productRepository.findProductById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product by id" + id + "was not found "));
    }

    @Override
    public void deleteProduct(long id) {
        productRepository.deleteProductById(id);
    }
}
