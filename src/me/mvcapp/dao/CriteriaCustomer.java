package me.mvcapp.dao;

/**
 * 模糊查询的Javabean
 * 封装查询条件，因为很多时候查询条件和domain类不同
 * @author Administrator
 *
 */
public class CriteriaCustomer {

    private String name;

    private String address;

    private String phone;

    public CriteriaCustomer() {
	
    }

    public CriteriaCustomer(String name, String address, String phone) {
	super();
	this.name = name;
	this.address = address;
	this.phone = phone;
    }

    public String getName() {
	if (name == null) {
	    name = "%%";
	} else {
	    name = "%" + name + "%";
	}
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getAddress() {
	if (address == null) {
	    address = "%%";
	} else {
	    address = "%" + address + "%";
	}
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
    }

    public String getPhone() {
	if (phone == null) {
	    phone = "%%";
	} else {
	    phone = "%" + phone + "%";
	}
	return phone;
    }

    public void setPhone(String phone) {
	this.phone = phone;
    }

    @Override
    public String toString() {
	return "CriteriaCustomer [name=" + name + ", address=" + address + ", phone=" + phone + "]";
    }

}
