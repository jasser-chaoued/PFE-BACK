package com.Backend.SubscriptionBillingAndPaymentManagement.repository;

import com.Backend.SubscriptionBillingAndPaymentManagement.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByusername(String username);

    User findUserByEmail(String email);


}
