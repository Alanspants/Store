package com.example.Store.dao.imp;

import com.example.Store.dao.CustomerDao;
import com.example.Store.domain.Customer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDaoImpJdbcTest {

    CustomerDao dao;


    @BeforeEach
    void setUp() {
        dao = new CustomerDaoImpJdbc();
    }

    @AfterEach
    void tearDown() {
        dao = null;
    }

    @Test
    void findByPk() {
        Customer customer = dao.findByPk("alan");
        assertNotNull(customer);
        assertEquals("alan", customer.getId());
        assertEquals("陈浩哲", customer.getName());
        assertEquals("123", customer.getPassword());
        assertEquals("安徽芜湖", customer.getAddress());
        assertEquals("888888", customer.getPhone());
        assertEquals(111111111, customer.getBirthday().getTime());

    }

    @Test
    void findAll() {
        List<Customer> list = dao.findAll();
        assertEquals(list.size(), 2);
    }

    @Test
    void create() {
        Customer customer = new Customer();
        customer.setId("OJ");
        customer.setName("蒋思瑶");
        customer.setPassword("123");
        customer.setAddress("广东深圳");
        customer.setPhone("1111111");
        customer.setBirthday(new Date(111231L));

        dao.create(customer);
        // 按照PK查询
        Customer customer1 = dao.findByPk("OJ");
        assertNotNull(customer);
        assertEquals("OJ", customer.getId());
        assertEquals("蒋思瑶", customer.getName());
        assertEquals("123", customer.getPassword());
        assertEquals("广东深圳", customer.getAddress());
        assertEquals("1111111", customer.getPhone());
        assertEquals(111231, customer.getBirthday().getTime());


    }

    @Test
    void modify() {
        Customer customer = new Customer();
        customer.setId("OJ");
        customer.setName("蒋思瑶111");
        customer.setPassword("123");
        customer.setAddress("广东深圳");
        customer.setPhone("1111111");
        customer.setBirthday(new Date(111231L));

        dao.modify(customer);
        // 按照PK查询
        Customer customer1 = dao.findByPk("OJ");
        assertNotNull(customer);
        assertEquals("OJ", customer.getId());
        assertEquals("蒋思瑶111", customer.getName());
        assertEquals("123", customer.getPassword());
        assertEquals("广东深圳", customer.getAddress());
        assertEquals("1111111", customer.getPhone());
        assertEquals(111231, customer.getBirthday().getTime());

    }

    @Test
    void remove() {

        dao.remove("OJ");

        Customer customer = dao.findByPk("OJ");
        assertNull(customer);

    }
}