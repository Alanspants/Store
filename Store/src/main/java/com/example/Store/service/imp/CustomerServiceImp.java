package com.example.Store.service.imp;

import com.example.Store.dao.CustomerDao;
import com.example.Store.dao.imp.CustomerDaoImpJdbc;
import com.example.Store.domain.Customer;
import com.example.Store.service.CustomerService;
import com.example.Store.service.ServiceException;

public class CustomerServiceImp implements CustomerService {

    CustomerDao customerDao = new CustomerDaoImpJdbc();

    @Override
    public boolean login(Customer customer) {

        Customer dbCustomer = customerDao.findByPk(customer.getId());

        if (dbCustomer.getPassword().equals(customer.getPassword())) {

            customer.setPhone(dbCustomer.getPhone());
            customer.setAddress(dbCustomer.getAddress());
            customer.setName(dbCustomer.getName());
            customer.setBirthday(dbCustomer.getBirthday());

            return true;
        }
        return false;
    }

    @Override
    public void register(Customer customer) throws ServiceException {

        Customer dbCustomer = customerDao.findByPk(customer.getId());

        if (dbCustomer != null) {//客户ID已经存在了
            throw new ServiceException("客户ID" + customer.getId() + "已经存在了");
        }

        // 注册开始
        customerDao.create(customer);
          
    }
}
