<%--suppress CheckTagEmptyBody --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
</head>
<body>
<c:if test="${requestScope.message != null}">
    <br>
    <span style="color: red; ">${requestScope.message}</span>
    <br><br>
</c:if>
<jsp:useBean id="customer" scope="request" class="me.mvcapp.domain.Customer"></jsp:useBean>
<c:set var="id" value="${customer.id != null?customer.id:param.id}"></c:set>
<c:set var="name" value="${customer.name != null?customer.name:param.name}"></c:set>
<c:set var="oldName" value="${customer.name != null?customer.name:param.oldName}"></c:set>
<c:set var="address" value="${customer.address != null?customer.address:param.address}"></c:set>
<c:set var="phone" value="${customer.phone != null?customer.phone:param.phone}"></c:set>
<form action="update.do" method="post">
    <input type="hidden" name="id" value="${id}"/>
    <input type="hidden" name="oldName" value="${oldName}"/>
    <table>
        <tr>
            <td>name:</td>
            <td>
                <label>
                    <input type="text" name="name" value="${name}"/>
                </label>
            </td>
        </tr>
        <tr>
            <td>address:</td>
            <td>
                <label>
                    <input type="text" name="address" value="${address}"/>
                </label>
            </td>
        </tr>
        <tr>
            <td>phone:</td>
            <td>
                <label>
                    <input type="text" name="phone" value="${phone}"/>
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