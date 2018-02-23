<%@ page import="me.mvcapp.domain.Customer" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>
<%
    Object message = request.getAttribute("message");
    if (message != null) {
%>
<br>
<span style="color: red; "><%=message%></span>
<br><br>
<%
    }
    String id = null;
    String name = null;
    String address = null;
    String phone = null;
    Customer customer = (Customer) request.getAttribute("customer");
    //如果不为空，说明是从update这个链接来的
    if (customer != null) {
        id = customer.getId() + "";
        name = customer.getName();
        address = customer.getAddress();
        phone = customer.getPhone();
        //如果是空，说明是出错之后返回的，（例如用户名占用报错等）if条件return了，此时应该获取update.do页面的参数
    } else {
        id = request.getParameter("id");
        name = request.getParameter("oldName");
        address = request.getParameter("address");
        phone = request.getParameter("phone");
    }
%>

<form action="update.do" method="post">
    <input type="hidden" name="id" value="<%=id%>"/>
    <input type="hidden" name="oldName" value="<%=name%>"/>
    <table>
        <tr>
            <td>name:</td>
            <td>
                <label>
                    <input type="text" name="name" value="<%= name %>"/>
                </label>
            </td>
        </tr>
        <tr>
            <td>address:</td>
            <td>
                <label>
                    <input type="text" name="address" value="<%= address %>"/>
                </label>
            </td>
        </tr>
        <tr>
            <td>phone:</td>
            <td>
                <label>
                    <input type="text" name="phone" value="<%= phone %>"/>
                </label>
            </td>
        </tr>
        <tr>
            <td colspan="2"><input type="submit" value="Submit"/></td>
        </tr>
    </table>
</form>

</body>
</html>