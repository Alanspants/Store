package com.example.Store.dao.imp;

import JDBCTemplate.JdbcTemplate;
import JDBCTemplate.PreparedStatementCreator;
import JDBCTemplate.RowCallbackHandler;
import com.example.Store.dao.OrderDao;
import com.example.Store.domain.Orders;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderDaoImpJdbc implements OrderDao {

    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @Override
    public Orders findByPk(String pk) {
        List<Orders> list = new ArrayList<Orders>();

        String sql = "select id, order_date, status, total from Orders where id = ?";

        jdbcTemplate.query(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, pk);
            return ps;
        }, rs -> {
            Orders orders = new Orders();
            orders.setOrderDate(new Date(rs.getLong("order_date")));
            orders.setStatus(rs.getInt("status"));
            orders.setTotal(rs.getDouble("total"));
            list.add(orders);
        });

        if(list.size() == 1){
            return list.get(0);
        }

        return null;
    }

    @Override
    public List<Orders> findAll() {
        List<Orders> list = new ArrayList<Orders>();

        String sql = "select id, order_date, status, total from Orders";

        jdbcTemplate.query(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql);
            return ps;
        }, rs -> {
            Orders orders = new Orders();
            orders.setOrderDate(new Date(rs.getLong("order_date")));
            orders.setStatus(rs.getInt("status"));
            orders.setTotal(rs.getDouble("total"));
            list.add(orders);
        });

        return list;
    }

    @Override
    public void create(Orders orders) {
        String sql = "insert into Orders values(?,?,?,?)";
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1, orders.getId());
                ps.setLong(2, orders.getOrderDate().getTime());
                ps.setInt(3, orders.getStatus());
                ps.setDouble(4, orders.getTotal());
                return ps;
            }
        });
    }

    @Override
    public void modify(Orders orders) {
        String sql = "update Orders set order_date=?, status = ?, total = ? where id = ?";
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql);

                ps.setLong(1, orders.getOrderDate().getTime());
                ps.setInt(2, orders.getStatus());
                ps.setDouble(3, orders.getTotal());
                ps.setString(4, orders.getId());
                return ps;
            }
        });
    }

    @Override
    public void remove(String pk) {
        String sql = "delete from orders where id = ?";
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                PreparedStatement ps = conn.prepareStatement(sql);

                ps.setString(1, pk);
                return ps;
            }
        });
    }
}
