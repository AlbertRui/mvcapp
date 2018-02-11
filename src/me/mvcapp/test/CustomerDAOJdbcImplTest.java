package me.mvcapp.test;

import java.util.List;

import org.junit.Test;

import me.mvcapp.dao.CriteriaCustomer;
import me.mvcapp.dao.CustomerDAO;
import me.mvcapp.dao.impl.CustomerDAOJdbcImpl;
import me.mvcapp.domain.Customer;

public class CustomerDAOJdbcImplTest {

    private CustomerDAO customerDAO = new CustomerDAOJdbcImpl();

    @Test
    public void testGetAll() {
        List<Customer> customers = customerDAO.getAll();
        for (Customer customer : customers) {
            System.out.println(customer);
        }
    }

    @Test
    public void testSaveCustomer() {
        Customer customer = new Customer("Tom", "Xian", "18834721357");
        customerDAO.save(customer);
    }

    @Test
    public void testGetInteger() {
        Customer customer = customerDAO.get(1);
        System.out.println(customer);
    }

    @Test
    public void testDelete() {
        customerDAO.delete(1);
    }

    @Test
    public void testGetCountWithName() {
        long count = customerDAO.getCountWithName("Jerry");
        System.out.println(count);
    }

    @Test
    public void testGetForListWithCriteriaCustomer() {
        CriteriaCustomer cc = new CriteriaCustomer("k", null, null);
        List<Customer> customers = customerDAO.getForListWithCriteriaCustomer(cc);
        if (customers != null && customers.size() > 0) {
            for (Customer customer : customers) {
                System.out.println(customer);
            }
        }
    }
}
