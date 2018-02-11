package me.mvcapp.dao;

import java.util.List;

import me.mvcapp.domain.Customer;

public interface CustomerDAO {

    List<Customer> getForListWithCriteriaCustomer(CriteriaCustomer cc);

    List<Customer> getAll();

    void save(Customer customer);

    Customer get(Integer id);

    void delete(Integer id);

    /**
     * 返回和name相等的记录数
     *
     * @param name : 对应数据库中的name
     */
    long getCountWithName(String name);
}
