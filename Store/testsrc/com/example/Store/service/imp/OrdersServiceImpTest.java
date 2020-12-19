package com.example.Store.service.imp;

import com.example.Store.dao.OrderDao;
import com.example.Store.dao.OrderLineItemDao;
import com.example.Store.dao.imp.OrderDaoImpJdbc;
import com.example.Store.dao.imp.OrderLineItemDaoImpJdbc;
import com.example.Store.domain.Orders;
import com.example.Store.service.OrdersService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class OrdersServiceImpTest {

    OrdersService ordersService;
    OrderDao orderDao = new OrderDaoImpJdbc();
    OrderLineItemDao orderLineItemDao = new OrderLineItemDaoImpJdbc();

    @BeforeEach
    void setUp() {
        ordersService = new OrdersServiceImp();
    }

    @AfterEach
    void tearDown() {
        ordersService = null;
    }

    @Test
    void submitOrders() {

        List<Map<String, Object>> cart = new ArrayList<Map<String, Object>>();
        Map<String, Object> item1 = new HashMap<String, Object>();
        item1.put("goodsid", 3);
        item1.put("quantity", 2);
        cart.add(item1);

        Map<String, Object> item2 = new HashMap<String, Object>();
        item2.put("goodsid", 8);
        item2.put("quantity", 3);
        cart.add(item2);

        String ordersid = ordersService.submitOrders(cart);
        assertNotNull(ordersid);

        Orders orders = orderDao.findByPk(ordersid);
        assertNotNull(orders);
        assertEquals(1, orders.getStatus());

        double total = 3099 * 2 + 1888 * 3;
        assertEquals(total, orders.getTotal());


    }
}