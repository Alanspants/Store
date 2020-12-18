package com.example.Store.service;

import com.example.Store.domain.Goods;

import java.util.List;

public interface GoodsService {
    List<Goods> queryAll();

    List<Goods> queryByStartEnd(int start, int end);

    Goods queryDetail(long goodsid);
}


