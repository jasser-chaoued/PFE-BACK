package com.Backend.SubscriptionBillingAndPaymentManagement.enumeration;

import static com.Backend.SubscriptionBillingAndPaymentManagement.constant.Authority.*;

public enum Role {

    ROLE_USER(USER_AUTHORITIES),
    ROLE_HR(HR_AUTHORITIES),
    ROLE_MANAGER(MANAGER_AUTHORITIES),
    ROLE_ADMIN(ADMIN_AUTHORITIES),
    ROLE_SUPER_ADMIN(SUPER_ADMIN_AUTHORITIES);

    private String[] authoroties;

    Role(String... authoroties) {
        this.authoroties = authoroties;
    }

    public String[] getAuthoroties (){
        return authoroties;
    }

}

