package com.example.Store.service.imp;

import com.example.Store.domain.Goods;
import com.example.Store.service.GoodsService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GoodsServiceImpTest {

    GoodsService goodsService;

    @BeforeEach
    void setUp() {
        goodsService = new GoodsServiceImp();
    }

    @AfterEach
    void tearDown() {
        goodsService = null;
    }

    @Test
    void queryAll() {
        List<Goods> list = goodsService.queryAll();
        assertEquals(34, list.size());
//
        Goods goods = list.get(2);
        assertEquals(3099, goods.getPrice());
    }

    @Test
    void queryByStartEnd() {
        List<Goods> list = goodsService.queryByStartEnd(0, 10);
        assertEquals(10, list.size());
    }

    @Test
    void queryDetail() {
        Goods goods = goodsService.queryDetail(3);
        assertEquals(3099, goods.getPrice());
    }
}