package com.example.Store.dao.imp;

import com.example.Store.dao.OrderDao;
import com.example.Store.domain.Orders;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderDaoImpJdbcTest {

    OrderDao dao;

    @BeforeEach
    void setUp() {
        dao = new OrderDaoImpJdbc();
    }

    @AfterEach
    void tearDown() {
        dao = null;
    }

    @Test
    void findByPk() {
//        Orders orders = dao.findByPk("");
    }

    @Test
    void findAll() {
    }

    @Test
    void create() {
    }

    @Test
    void modify() {
    }

    @Test
    void remove() {
    }
}