package com.example.Store.service;

import com.example.Store.domain.Customer;

public interface CustomerService {

    /**
     * 处理客户登陆
     * @param customer
     * @return 登陆成功返回true, 登陆失败返回false
     */
    boolean login(Customer customer);

    /**
     * 处理客户注册
     * @param customer
     * @throws ServiceException 注册失败抛出异常
     */
    void register(Customer customer) throws ServiceException;

}
