package org.acme.entity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Test Customer Validation")
class CustomerValidationTest {

    @Test
    void testNullCheck() {
        Customer customer = new Customer();
        customer.setName(null);
        customer.setPrename(null);
        customer.setAddress(null);
        assertEquals(customer.validation().size(), 3);
    }

    @Test
    void testGreaterThanOrEqual() {
        Customer customer = new Customer();
        customer.setName("a");
        customer.setPrename("b");
        customer.setAddress("ij");
        assertEquals(customer.validation().size(), 3);
    }

    @Test
    void testLessThanOrEqual() {
        Customer customer = new Customer();
        customer.setName("aqdwkjnaslkdnlwjqwjeqjwne");
        customer.setPrename("basdwqdbkhadkwiuahwdupauwhd");
        customer.setAddress("nasjdlwnqeidnsadbhwbenwiud");
        assertEquals(customer.validation().size(), 3);
    }
}