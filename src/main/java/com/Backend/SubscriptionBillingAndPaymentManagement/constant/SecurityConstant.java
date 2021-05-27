package com.Backend.SubscriptionBillingAndPaymentManagement.constant;

public class SecurityConstant {

    public static final long EXPIRATION_TIME = 432000000 ; // days in milliseconds
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String JWT_TOKEN_HEADER = "Jwt-Token";
    public static final String TOKEN_CANNOT_BE_VERIFIED = "TOKEN CANNOT BE VERIFIED";
    public static final String TEAMWILL_LLC = "TeamWill, LLC";
    public static final String TEAMWILL_ADMINISTRATION = "User management Portal";
    public static final String AUTHORITIES = "Authorities";
    public static final String FORBIDDEN_MESSAGE = "You need to login to access this page ";
    public static final String ACCESS_DENIED_MESSAGE = "You do not have permission to access this page ";
    public static final String OPTIONS_HTTP_METHOD = "OPTIONS";
    public static final String[] PUBLIC_URLS = {"/user/login","/user/register", "/user/image/**", "/products/**/" };
   // public static final String[] PUBLIC_URLS = { "**" };


}
