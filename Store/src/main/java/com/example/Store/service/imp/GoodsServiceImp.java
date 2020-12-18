package com.example.Store.service.imp;

import com.example.Store.dao.GoodsDao;
import com.example.Store.dao.imp.GoodsDaoImpJdbc;
import com.example.Store.domain.Goods;
import com.example.Store.service.GoodsService;

import java.util.List;

public class GoodsServiceImp implements GoodsService {

    GoodsDao goodsDao = new GoodsDaoImpJdbc();

    @Override
    public List<Goods> queryAll() {
        return goodsDao.findAll();
    }

    @Override
    public List<Goods> queryByStartEnd(int start, int end) {
        return goodsDao.findStardEnd(start, end);
    }

    @Override
    public Goods queryDetail(long goodsid) {
        return goodsDao.findByPk(goodsid);
    }
}
