package me.mvcapp.servlet;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.mvcapp.dao.CriteriaCustomer;
import me.mvcapp.dao.CustomerDAO;
import me.mvcapp.dao.impl.CustomerDAOJdbcImpl;
import me.mvcapp.domain.Customer;

/**
 * Servlet implementation class CustomerServlet
 */
public class CustomerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private CustomerDAO customerDAO = new CustomerDAOJdbcImpl();

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//	    throws ServletException, IOException {
//	String method = request.getParameter("method");
//
//	switch (method) {
//	case "add":
//	    add(request, response);
//	    break;
//	case "query":
//	    query(request, response);
//	    break;
//	case "delete":
//	    delete(request, response);
//	}
//    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	String methodName = request.getServletPath();
	System.out.println(methodName);
	methodName = methodName.substring(5, methodName.length() - 3);
	try {
	    //利用反射获取methodName对应的方法
	    Method method = getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
	    //利用反射调用方法
	    method.invoke(this, request, response);
	} catch (Exception e) {
	    response.sendRedirect("error.jsp");
	}
    }
    
    @SuppressWarnings("unused")
    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
	String idStr = request.getParameter("id");
	
	int id = 0;
	//防止id无法转为int类型。若不能转，id=0
	try {
	    id = Integer.parseInt(idStr);
	    customerDAO.delete(id);
	} catch (Exception e) {
	}
	response.sendRedirect("query.do");
    }

    @SuppressWarnings("unused")
    private void query(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	//获取模糊查询的请求参数
	String name = request.getParameter("name");
	String address = request.getParameter("address");
	String phone = request.getParameter("phone");
	
	//把得到的请求参数封装为一个CriteriaCustomer对象
	CriteriaCustomer cc = new CriteriaCustomer(name, address, phone);
	//1.调用CustomerDAO的getForListWithCriteriaCustomer()方法得到Customer的集合
	List<Customer> customers = customerDAO.getForListWithCriteriaCustomer(cc);
	//2.把Customer的集合放入到request中
	request.setAttribute("customers", customers);
	//3.转发页面倒index.jsp(不能使用重定向)
	request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    @SuppressWarnings("unused")
    private void add(HttpServletRequest request, HttpServletResponse response) {
	//1.获取表单参数 name, address, phone
	
	//2.检验name是否已经被占用:
	
	//2.1调用customerDAO的getCountWithName(String name)方法,检查name在数据库中是否已经存在
	
	//2.2若返回值大于零，则响应 newCustomer.jsp页面
	//通过转发的方式响应newCustomer.jsp
	
	//2.2.1要求在newCustomer.js页面显示一个消息：用户名name已经被占用，请重新选择！！！
	//在request中放入一个属性message：用户名已经被占用,请重新选择！
	//在页面上通过request.getAttribute("message")的方式显示
	
	//2.2.2newCustomer.jsp的表单值可以回显
	//通过value="<%= request.getParameter("name") == null ? "" : request.getParameter("name") %>"来进行回显
	
	//2.2.3结束方法
	
	//3.若验证通过，则把表单参数封装为一个Customer对象？customer
	
	//4.。调用CustomerDAO的save(Customer customer)方法执行保存操作
	
	//5。重定向到success.jsp页面
    }

}
