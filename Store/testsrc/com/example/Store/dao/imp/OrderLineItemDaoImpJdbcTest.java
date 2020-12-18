package com.example.Store.dao.imp;

import com.example.Store.dao.OrderLineItemDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderLineItemDaoImpJdbcTest {

    OrderLineItemDao dao;

    @BeforeEach
    void setUp() {
        dao = new OrderLineItemDaoImpJdbc();
    }

    @AfterEach
    void tearDown() {
        dao = null;
    }

    @Test
    void findByPk() {

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