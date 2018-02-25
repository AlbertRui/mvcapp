<%--suppress CheckTagEmptyBody --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <script type="text/javascript" src="${pageContext.request.contextPath}/scripts/jquery-1.7.2.js"></script>
    <script type="text/javascript">
        $(function () {
            $(".delete").click(function () {
                var content = $(this).parent().parent().find("td:eq(1)").text();
                return confirm("确定要删除" + content + "的信息吗？");
            });
        });
    </script>
</head>
<body>

<form action="query.do" method="post">
    <table>
        <tr>
            <td>name:</td>
            <td>
                <label>
                    <input type="text" name="name"/>
                </label>
            </td>
        </tr>
        <tr>
            <td>address:</td>
            <td>
                <label>
                    <input type="text" name="address"/>
                </label>
            </td>
        </tr>
        <tr>
            <td>phone:</td>
            <td>
                <label>
                    <input type="text" name="phone"/>
                </label>
            </td>
        </tr>
        <tr>
            <td><input type="submit" value="Query"/></td>
            <td><a href="newCustomer.jsp">ADD New Customer</a></td>
        </tr>
    </table>
</form>

<br><br>

<c:if test="${!empty requestScope.customers}">
    <hr>
    <br><br>
    <table border="1" cellpadding="10" cellspacing="0">
        <tr>
            <th>ID</th>
            <th>CustomerName</th>
            <th>Address</th>
            <th>Phone</th>
            <th>UPDATE/DELETE</th>
        </tr>
        <c:forEach items="${requestScope.customers}" var="customer">
            <tr>
                <td>${customer.id}</td>
                <td>${customer.name}</td>
                <td>${customer.address}</td>
                <td>${customer.phone}</td>
                <td>
                    <c:url value="edit.do" var="editurl">
                        <c:param name="id" value="${customer.id}"></c:param>
                    </c:url>
                    <a href="${editurl}">UPDATE</a>
                    <c:url value="delete.do" var="deleteurl">
                        <c:param name="id" value="${customer.id}"></c:param>
                    </c:url>
                    <a href="${deleteurl}" class="delete">DELETE</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</c:if>

</body>
</html>