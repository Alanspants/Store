package com.example.Store.dao;

import com.example.Store.domain.Goods;

import java.util.List;

public interface GoodsDao {
    Goods findByPk(long pk);

    List<Goods> findAll();

    void create(Goods goods);

    void modify(Goods goods);

    void remove(String pk);
}
