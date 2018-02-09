package me.mvcapp.dao;

import java.util.List;

import me.mvcapp.domain.Customer;

public interface CustomerDAO {
    
    public List<Customer> getForListWithCriteriaCustomer(CriteriaCustomer cc);

    public List<Customer> getAll();
    
    public void save(Customer customer);
    
    public Customer get(Integer id);
    
    public void delete(Integer id);
    
    /**
     * 返回和name相等的记录数
     * @param name
     * @return
     */
    public long getCountWithName(String name);
}
