package com.example.Store.dao.imp;

import JDBCTemplate.JdbcTemplate;
import JDBCTemplate.PreparedStatementCreator;
import JDBCTemplate.RowCallbackHandler;
import com.example.Store.dao.OrderLineItemDao;
import com.example.Store.domain.Goods;
import com.example.Store.domain.OrderLineItem;
import com.example.Store.domain.Orders;
import com.sun.tools.corba.se.idl.constExpr.Or;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderLineItemDaoImpJdbc implements OrderLineItemDao {

    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @Override
    public OrderLineItem findByPk(long pk) {

        List<OrderLineItem> list = new ArrayList<OrderLineItem>();

        String sql = "select id, goodsid, orderid, quantity, sub_total from OrderLineItems where id = ?";
        jdbcTemplate.query(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setLong(1, pk);
            return ps;
        }, rs -> {
            OrderLineItem lineItem = new OrderLineItem();
            lineItem.setId(rs.getLong("id"));
            lineItem.setQuantity(rs.getInt("quantity"));
            lineItem.setSubTotal(rs.getFloat("sub_total"));

            Orders orders = new Orders();
            orders.setId(rs.getString("goodsid"));
            lineItem.setOrders(orders);

            Goods goods = new Goods();
            goods.setId(rs.getLong("orderid"));
            lineItem.setGoods(goods);

            list.add(lineItem);
        });
        if(list.size() == 1)
            return list.get(0);
        return null;
    }

    @Override
    public List<OrderLineItem> findAll() {
        List<OrderLineItem> list = new ArrayList<OrderLineItem>();

        String sql = "select id, goodsid, orderid, quantity, sub_total from OrderLineItems";
        jdbcTemplate.query(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql);
            return ps;
        }, rs -> {
            OrderLineItem lineItem = new OrderLineItem();
            lineItem.setId(rs.getLong("id"));
            lineItem.setQuantity(rs.getInt("quantity"));
            lineItem.setSubTotal(rs.getFloat("sub_total"));

            Orders orders = new Orders();
            orders.setId(rs.getString("orderid"));
            lineItem.setOrders(orders);

            Goods goods = new Goods();
            goods.setId(rs.getLong("goodsid"));
            lineItem.setGoods(goods);

            list.add(lineItem);
        });
        return list;
    }

    @Override
    public void create(OrderLineItem lineItem) {
        jdbcTemplate.update(new PreparedStatementCreator() {
            String sql = "insert into OrderLineItems values (?,?,?,?,?)";
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setLong(1, lineItem.getId());
                ps.setLong(2, lineItem.getGoods().getId());
                ps.setString(3, lineItem.getOrders().getId());
                ps.setInt(4, lineItem.getQuantity());
                ps.setDouble(5, lineItem.getSubTotal());

                return ps;
            }
        });
    }

    @Override
    public void modify(OrderLineItem lineItem) {
        jdbcTemplate.update(new PreparedStatementCreator() {
            String sql = "update OrderLineItems set goodsid = ?, orderid=?, quantity=?, sub_total=? where id = ?";
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql);

                ps.setLong(1, lineItem.getGoods().getId());
                ps.setString(2, lineItem.getOrders().getId());
                ps.setInt(3, lineItem.getQuantity());
                ps.setDouble(4, lineItem.getSubTotal());
                ps.setLong(5, lineItem.getId());

                return ps;
            }
        });
    }

    @Override
    public void remove(int pk) {
        jdbcTemplate.update(new PreparedStatementCreator() {
            String sql = "remove from OrderLineItems where id = ?";
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql);

                ps.setInt(1, pk);


                return ps;
            }
        });
    }
}
