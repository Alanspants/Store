package com.example.Store.service.imp;

import com.example.Store.dao.GoodsDao;
import com.example.Store.dao.OrderDao;
import com.example.Store.dao.OrderLineItemDao;
import com.example.Store.dao.imp.GoodsDaoImpJdbc;
import com.example.Store.dao.imp.OrderDaoImpJdbc;
import com.example.Store.dao.imp.OrderLineItemDaoImpJdbc;
import com.example.Store.domain.Goods;
import com.example.Store.domain.OrderLineItem;
import com.example.Store.domain.Orders;
import com.example.Store.service.OrdersService;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrdersServiceImp implements OrdersService {

    GoodsDao goodsDao = new GoodsDaoImpJdbc();
    OrderDao orderDao = new OrderDaoImpJdbc();
    OrderLineItemDao orderLineItemDao = new OrderLineItemDaoImpJdbc();

    @Override
    public String submitOrders(List<Map<String, Object>> cart) {
        Orders orders = new Orders();
        Date date = new Date();
        String ordersid = String.valueOf(date.getTime()) + String.valueOf((int)(Math.random() * 100));

        orders.setId(ordersid);
        orders.setOrderDate(new Date());
        orders.setStatus(1);
        orders.setTotal(0);
        //将订单插入到数据库中
        orderDao.create(orders);

        double total = 0.0;

        for (Map<String, Object> item : cart){
            //item的结构：[商品id，数量]
            int goodsid = (int)item.get("goodsid");
            Integer quantity = (Integer) item.get("quantity");
            Goods goods = goodsDao.findByPk(goodsid);
            // 小计
            double subtotal = quantity * goods.getPrice();
            total += subtotal;

            OrderLineItem lineItem = new OrderLineItem();
            lineItem.setQuantity(quantity);
            lineItem.setGoods(goods);
            lineItem.setOrders(orders);
            lineItem.setSubTotal(subtotal);

            orderLineItemDao.create(lineItem);
        }

        orders.setTotal(total);
        orderDao.modify(orders);
        return ordersid;
    }
}
