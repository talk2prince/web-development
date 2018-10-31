<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Display Page</title>
</head>
<body>


	<c:if test="${empty user}">
		<c:redirect url="home.html"></c:redirect>
	</c:if>
	<p style="text-align: right;">
		<span> Profile:${user} </span> <a href="logout.html">Logout</a>
	</p>
	<h2 style="text-align: center;">Employee Leave Information System</h2>
	<hr>
	<table border="1" align="center">

		<tr style="text-align: center">
			<th>Type of Leave</th>
			<th>Max Allowed</th>
			<th>Availed so far</th>
		</tr>
		<c:forEach var="ltype" items="${ltypes}">
			<tr style="text-align: center">
				<td>${ltype.typeName}</td>
				<td>${ltype.maximum}</td>
				<td>??</td>
			</tr>
		</c:forEach>
	</table>

	<hr>
	<p>${msg}</p>
	<form action="newleave.html" method="post">
		<label>Leave type:</label> 
		<input type="hidden" name="uname" value="${user}">
		<select name="leaveType">
			<option value="">Select Leave Type</option>
			<c:forEach var="ltype" items="${ltypes}">
				<option value="${ltype.leaveId}">${ltype.typeName}</option>
			</c:forEach>
		</select> 
		<label>Start Date:</label> <input type="date" name="startDate">
		<label>End Date:</label> <input type="date" name="endDate"> 
		<input type="submit" value="Create Leave">
	</form>
	<hr>
	<table border="2" align="center">
		<tr>
			<th>Sr.No.</th>
			<th>Leave Type</th>
			<th>Start Date</th>
			<th>End Date</th>
			<th>No of Days</th>
		</tr>
		<c:if test="${empty records}">
			<tr>
				<td colspan="5" style="text-align: center;">No Leaves to
					display!</td>
			</tr>
		</c:if>
		<c:forEach var="record" items="${records}">
			<tr>
			<th></th>
			<th>${record.categoryId}</th>
			<th>${record.startDate}</th>
			<th>${record.endDate}</th>
			<th>${record.noOfDays}</th>
		</tr>
		</c:forEach>
	</table>

</body>
</html>