<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>

	<form action="add.do" method="post">
		<table>
			<tr>
				<td>name:</td>
				<td><input type="text" name="name" value="<%= request.getParameter("name") == null ? "" : request.getParameter("name") %>"/></td>
			</tr>
			<tr>
				<td>address:</td>
				<td><input type="text" name="address"/></td>
			</tr>
			<tr>
				<td>phone:</td>
				<td><input type="text" name="phone"/></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="Submit"/></td>
			</tr>
		</table>
	</form>

</body>
</html>