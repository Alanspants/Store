package com.example.Store.dao.imp;

import JDBCTemplate.JdbcTemplate;
import com.example.Store.dao.CustomerDao;
import com.example.Store.domain.Customer;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerDaoImpJdbc implements CustomerDao {

    JdbcTemplate jdbcTemplate = new JdbcTemplate();

    @Override
    public Customer findByPk(String pk) {
        List<Customer> list = new ArrayList<Customer>();
        String sql = "select id, name, password, address, phone, birthday from Customers where id = ?;";

        jdbcTemplate.query(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, pk);
            return ps;
        }, rs -> {
            Customer customer = new Customer();
            customer.setId(rs.getString("id"));
            customer.setName(rs.getString("name"));
            customer.setPassword(rs.getString("password"));
            customer.setAddress(rs.getString("address"));
            customer.setPhone(rs.getString("phone"));
            customer.setBirthday(new Date(rs.getLong("birthday")));

            list.add(customer);
        });
        if (list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> list = new ArrayList<Customer>();
        String sql = "select id, name, password, address, phone, birthday from Customers";

        jdbcTemplate.query(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql);
            return ps;
        }, rs -> {
            Customer customer = new Customer();
            customer.setId(rs.getString("id"));
            customer.setName(rs.getString("name"));
            customer.setPassword(rs.getString("password"));
            customer.setAddress(rs.getString("address"));
            customer.setPhone(rs.getString("phone"));
            customer.setBirthday(new Date(rs.getLong("birthday")));

            list.add(customer);
        });
        return list;
    }

    @Override
    public void create(Customer customer) {

        String sql = "insert into Customers values (?,?,?,?,?,?)";

        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, customer.getId());
            ps.setString(2, customer.getName());
            ps.setString(3, customer.getPassword());
            ps.setString(4, customer.getAddress());
            ps.setString(5, customer.getPhone());
            ps.setLong(6, customer.getBirthday().getTime());

            return ps;
        });
    }

    @Override
    public void modify(Customer customer) {
        String sql = "update Customers set name=?, password=?, address=?, phone=?, birthday=? where id=?";
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, customer.getName());
            ps.setString(2, customer.getPassword());
            ps.setString(3, customer.getAddress());
            ps.setString(4, customer.getPhone());
            ps.setLong(5, customer.getBirthday().getTime());
            ps.setString(6, customer.getId());

            return ps;
        });
    }

    @Override
    public void remove(String pk) {
        String sql = "delete from Customers where id = ?";
        jdbcTemplate.update(conn -> {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, pk);
            return ps;
        });
    }
}
