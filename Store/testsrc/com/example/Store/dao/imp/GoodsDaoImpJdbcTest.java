package com.example.Store.dao.imp;

import com.example.Store.dao.GoodsDao;
import com.example.Store.domain.Customer;
import com.example.Store.domain.Goods;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GoodsDaoImpJdbcTest {

    GoodsDao dao;

    @BeforeEach
    void setUp() {
        dao = new GoodsDaoImpJdbc();
    }

    @AfterEach
    void tearDown() {
        dao = null;
    }

    @Test
    void findByPk() {
        Goods goods = dao.findByPk(1);
        assertNotNull(goods);
        assertEquals(1, goods.getId());

    }

    @Test
    void findAll() {
        List<Goods> list = dao.findAll();
        assertEquals(34, list.size());
    }

    @Test
    void findStardEnd() {
        List<Goods> list = dao.findStardEnd(0, 10);
        assertEquals(10, list.size());
    }

    @Test
    void create() {
    }

    @Test
    void modify() {
    }
}