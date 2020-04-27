<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="${pageContext.request.contextPath}/css/popDialog.css"
	rel="stylesheet">
<!--  Add bootstrap style -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>View Orders Page</title>
</head>
<body>
	<div id="message">
		<c:if test="${not empty message}">
			<jsp:include page="messagePage.jsp" flush="true" />
		</c:if>
	</div>

	<div align="center" class="container">
		<a class="btn btn-default" role="button" href="/OnlineCabBooking/home.htm">back</a>
		<a class="btn btn-info" role="button" href="#" onClick="refreshOrders()">Refresh</a> 
		<a class="btn btn-success" role="button" href="#" onClick="editPageDialog(0)">Create one order</a>
	</div>

	<!-- Table for showing the list of orders -->
	<div align="center" id="ordersList">
		<jsp:include page="ordersList.jsp" flush="true" />
	</div>
	<div class="container">
		<a class="btn btn-dark" role="button" href="#" onClick="deleteOrders()">Delete Selected Records</a>
	</div>
	
	<div id="content" class="white_content">
		<div id="editForm"></div>
		<br> <a href="#" onClick="hideDialog()">close</a>
	</div>
	<div id="fade" class="black_overlay"></div>

	<!-- Import scripts -->
	<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/popDialog.js"></script>
	<script src="${pageContext.request.contextPath}/js/passenger/editOrderPage.js"></script>
	<script src="${pageContext.request.contextPath}/js/passenger/viewOrdersPage.js"></script>

</body>
</html>