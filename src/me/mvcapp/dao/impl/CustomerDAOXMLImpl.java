package me.mvcapp.dao.impl;

import me.mvcapp.dao.CriteriaCustomer;
import me.mvcapp.dao.CustomerDAO;
import me.mvcapp.domain.Customer;

import java.util.List;

/**
 * @author AlbertRui
 * @create 2018-02-22 9:53
 */
public class CustomerDAOXMLImpl implements CustomerDAO{
    @Override
    public List<Customer> getForListWithCriteriaCustomer(CriteriaCustomer cc) {
        return null;
    }

    @Override
    public List<Customer> getAll() {
        return null;
    }

    @Override
    public void save(Customer customer) {

    }

    @Override
    public Customer get(Integer id) {
        return null;
    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public void update(Customer customer) {

    }

    /**
     * 返回和name相等的记录数
     *
     * @param name : 对应数据库中的name
     */
    @Override
    public long getCountWithName(String name) {
        return 0;
    }
}
