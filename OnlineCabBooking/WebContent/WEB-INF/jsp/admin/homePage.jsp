<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<!--  Add bootstrap style -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css"
	integrity="sha384-/Y6pD6FV/Vv2HJnA6t+vslU6fwYXjCFtcEpHbNJ0lyAFsXTsjBbfaDjzALeQsN6M"
	crossorigin="anonymous">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">

<link href="${pageContext.request.contextPath}/css/popDialog.css"
	rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=uft-8">
<title>Admin Home Page</title>
</head>
<body>

	<div class="container-fluid">
		${userName} ${welcomeMessage}
		<br/>
		<a class="btn btn-outline-danger" role="button" href="logout.htm">Logout</a>
	</div>
	<div id="content" class="white_content">
		<div id="editForm"></div>
		<br> <a href="#" onClick="hideDialog()">close</a>
	</div>
	<div id="fade" class="black_overlay"></div>

	<!--  Add scripts -->
	<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/popDialog.js"></script>
	<script src="${pageContext.request.contextPath}/js/editOrderPage.js"></script>
</body>
</html>
