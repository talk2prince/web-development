<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register Page</title>
</head>
<body>

	<c:if test="${not empty user}">
		<c:redirect url="display.html"></c:redirect>
	</c:if>
	<h1>Employee Leave Management System</h1>
	<h3>Please enter your details:</h3>
	<h4>${result}</h4>
	<form action="empsave.html" method="post">
		<table>
			<tr>
				<td>Name:</td>
				<td><input type="text" name="name" /></td>
			</tr>
			<tr>
				<td>Username:</td>
				<td><input type="text" name="uname" /></td>
			</tr>
			<tr>
				<td>Password:</td>
				<td><input type="password" name="password" /></td>
			</tr>
			<tr>
				<td>Salary:</td>
				<td><input type="text" name="salary" /></td>
			</tr>
			<tr>
				<td>Gender:</td>
				<td><input type="radio" name="gender" value="M" />Male <input
					type="radio" name="gender" value="F" />Female</td>
			</tr>
			<tr>
				<td colspan="2" align="right"><input type="submit" value="Save" />
				</td>
			</tr>
			<tr>
				<td colspan="2" align="right"><a href="home.html">Go back</a></td>
			</tr>
		</table>
	</form>
</body>
</html>