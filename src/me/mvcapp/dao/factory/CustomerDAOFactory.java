package me.mvcapp.dao.factory;

import me.mvcapp.dao.CustomerDAO;
import me.mvcapp.dao.impl.CustomerDAOJdbcImpl;
import me.mvcapp.dao.impl.CustomerDAOXMLImpl;

import java.util.HashMap;
import java.util.Map;

/**
 * @author AlbertRui
 * @create 2018-02-22 10:09
 */
public class CustomerDAOFactory {

    private Map<String, CustomerDAO> daos = new HashMap<>();
    private String type = null;
    private CustomerDAOFactory() {
        daos.put("jdbc", new CustomerDAOJdbcImpl());
        daos.put("xml", new CustomerDAOXMLImpl());
    }

    private static CustomerDAOFactory instance = new CustomerDAOFactory();

    public static CustomerDAOFactory getInstance() {
        return instance;
    }
    public void setType(String type) {
        this.type = type;
    }
    public CustomerDAO getCustomerDAO() {
        return daos.get(type);
    }
}
