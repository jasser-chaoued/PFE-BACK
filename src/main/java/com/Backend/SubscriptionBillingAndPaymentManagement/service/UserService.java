package com.Backend.SubscriptionBillingAndPaymentManagement.service;

import com.Backend.SubscriptionBillingAndPaymentManagement.domain.User;
import com.Backend.SubscriptionBillingAndPaymentManagement.exception.domain.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

public interface UserService {

    User register (String firstName,String lastName, String username, String email) throws UsernameExistException, EmailExistException, MessagingException;

    List<User> getUsers();

    User findUserByUsername(String username);

    User findUserByEmail (String email);

    User addNewUser(String firstName, String lastName, String username, String email, String role, boolean isNotLocked, boolean isActive, MultipartFile profileImage) throws UserNotFoundException, UsernameExistException, EmailExistException, IOException, NotAnImageFileException;

    User updateUser(String currentUsername,String newFirstName, String newLastName, String newUsername, String newEmail, String role, Boolean isNotLocked, Boolean isActive, MultipartFile profileImage) throws UsernameExistException, EmailExistException, IOException, NotAnImageFileException;

    void deleteUser(String username) throws IOException;

    void resetPassword(String email) throws MessagingException, EmailNotFoundException;

    User updateProfileImage(String username, MultipartFile profileImage) throws UsernameExistException, EmailExistException, IOException, NotAnImageFileException;


}
