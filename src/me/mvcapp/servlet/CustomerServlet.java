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
     * response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     * response)
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String methodName = request.getServletPath();
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
    private void  edit(HttpServletRequest request, HttpServletResponse response) {
        //1.获取请求参数id
        //2.调用CustomerDao的getId(id);方法获取和id对应的customer对象
        //3.将customer放入到request中
        //4.响应updateCustomer.jsp页面，转发
    }

    @SuppressWarnings("unused")
    private void update(HttpServletRequest request, HttpServletResponse response) {
        //1.获取表单参数：id， name， address， phone， oldName
        //2.检查name是否已经被占用
        //2.1比较name和oldName是否相同，若相同，说明name可用
        //2.1若不相同，则调用customerDAO的getCountWithName(String name)方法,检查name在数据库中是否已经存在
        //2.2若返回值大于零，则响应 updateCustomer.jsp页面，通过转发的方式响应updateCustomer.jsp
        //2.2.1要求在updateCustomer.js页面显示一个消息：用户名name已经被占用，请重新选择！！！
        //在request中放入一个属性message：用户名已经被占用,请重新选择！
        //在页面上通过request.getAttribute("message")的方式显示

        //2.2.2newCustomer.jsp的表单值可以回显
        //通过value="<%= request.getParameter("name") == null ? "" : request.getParameter("name") %>"来进行回显
        //address,phone显示表单提交的新的值，而name显示oldName，而不是新提交的name
        //2.2.3结束方法

        //3.若验证通过，则把表单参数封装为一个Customer对象？customer
        //Customer customer = new Customer(name, address, phone);
        //4.。调用CustomerDAO的update(Customer customer)方法执行保存操作
        //customerDAO.save(customer);
        //5。重定向到success.jsp页面
    }

    @SuppressWarnings("unused")
    private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String idStr = request.getParameter("id");

        int id;
        //防止id无法转为int类型。若不能转，id=0
        try {
            id = Integer.parseInt(idStr);
            customerDAO.delete(id);
        } catch (Exception ignored) {
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
        String name = request.getParameter("name");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        //2.检验name是否已经被占用:

        //2.1调用customerDAO的getCountWithName(String name)方法,检查name在数据库中是否已经存在
        long count = customerDAO.getCountWithName(name);
        //2.2若返回值大于零，则响应 newCustomer.jsp页面
        //通过转发的方式响应newCustomer.jsp
        if (count > 0) {
            //2.2.1要求在newCustomer.js页面显示一个消息：用户名name已经被占用，请重新选择！！！
            //在request中放入一个属性message：用户名已经被占用,请重新选择！
            //在页面上通过request.getAttribute("message")的方式显示
            request.setAttribute("message", "用户名" + name + "已经被占用,请重新选择！");
            //2.2.2newCustomer.jsp的表单值可以回显
            //通过value="<%= request.getParameter("name") == null ? "" : request.getParameter("name") %>"来进行回显
            //2.2.3结束方法
            try {
                request.getRequestDispatcher("newCustomer.jsp").forward(request, response);
            } catch (ServletException | IOException e) {
                e.printStackTrace();
            }
            return;
        }
        //3.若验证通过，则把表单参数封装为一个Customer对象？customer
        Customer customer = new Customer(name, address, phone);
        //4.。调用CustomerDAO的save(Customer customer)方法执行保存操作
        customerDAO.save(customer);
        //5。重定向到success.jsp页面,使用重定向可以避免表单的重复提交问题
        try {
            response.sendRedirect("success.jsp");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
