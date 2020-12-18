package com.example.Store.service.imp;

import com.example.Store.domain.Customer;
import com.example.Store.service.CustomerService;
import com.example.Store.service.ServiceException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceImpTest {

    CustomerService customerService;

    @BeforeEach
    void setUp() {
        customerService = new CustomerServiceImp();
    }

    @AfterEach
    @DisplayName("登陆成功")
    void tearDown() {
        customerService = null;
    }

    @Test
    @DisplayName("登陆成功")
    void login1() {
        Customer customer = new Customer();
        customer.setId("alan");
        customer.setPassword("123");

        assertTrue(customerService.login(customer));
        assertNotNull(customer);
        assertEquals("陈浩哲", customer.getName());
    }

    @Test
    @DisplayName("登陆失败")
    void login2() {
        Customer customer = new Customer();
        customer.setId("alan");
        customer.setPassword("1234");

        assertFalse(customerService.login(customer));

    }

    @Test
    @DisplayName("注册成功")
    void register() throws ServiceException {
        Customer customer = new Customer();
        customer.setId("OJ");
        customer.setName("蒋思瑶");
        customer.setPassword("123");
        customer.setAddress("广东深圳");
        customer.setPhone("11111111");
        customer.setBirthday(new Date(111234L));

        customerService.register(customer);

        Customer customer1 = new Customer();
        customer1.setId("OJ");
        customer1.setPassword("123");
        assertTrue(customerService.login(customer1));
        assertNotNull(customer1);


    }
}