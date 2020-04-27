<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!--  Add bootstrap style -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta/css/bootstrap.min.css">


<div class="container">
	<div class="panel-group">
		<c:if test="${not empty ordersList}">
			<div class="panel panel-info">
				<div class="panel-heading">Individual Orders</div>
				<div class="panel-body">
					<table style="width: auto !important" class="table table-striped">
						<thead>
							<tr>
								<th>Select</th>
								<th>Order ID</th>
								<th>Cab</th>
								<th>Pickup Time</th>
								<th>Number of Passengers</th>
								<th>Update Time</th>
								<th>Status</th>
								<th>Operation</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${ordersList}" var="order" varStatus="os">
								<tr>
									<td align="center"><c:if
											test="${order['statusCode'] == 1}">
											<input type="checkbox" name="orderChbx"
												value="${order['id']}">
										</c:if></td>
									<td align="center"><a href="#"
										onClick="viewOneOrder(${order['id']})">${order['id']}</a></td>
									<td align="center">${order['cabPlate']}</td>
									<td align="center"><fmt:formatDate
											value="${order['pickupTime']}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
									<td align="center">${order['passengerNum']}</td>
									<td align="center"><fmt:formatDate
											value="${order['updateTime']}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
									<td align="center">${order['statusName']}</td>
									<td><c:if test="${order['statusCode'] == 1}">
											<a class="btn btn-primary btn-small" role="button" href="#"
												onClick="editPageDialog(${order['id']})"
												data-toggle="tooltip" data-placement="bottom"
												title="Click to edit the order">edit</a>
										</c:if> <c:if test="${order['statusCode'] != 2}">
											<a class="btn btn-secondary btn-small" role="button" href="#"
												onClick="deleteOneOrder(${order['id']})">delete</a>
										</c:if></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</c:if>

		<br>
		<c:if test="${not empty businessOrders}">
			<div class="panel panel-info">
				<div class="panel-heading">Business Orders</div>
				<div class="panel-body">

					<table style="width: auto !important" class="table table-striped">
						<thead>
							<tr>
								<th>Select</th>
								<th>Order ID</th>
								<th>Cab</th>
								<th>Pickup Time</th>
								<th>Number of Passengers</th>
								<th>Update Time</th>
								<th>Status</th>
								<th>Operation</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${businessOrders}" var="order" varStatus="os">
								<tr>
									<td align="center"><c:if
											test="${order['statusCode'] == 1}">
											<input type="checkbox" name="orderChbx"
												value="${order['id']}">
										</c:if></td>
									<td align="center"><a href="#"
										onClick="viewOneOrder(${order['id']})">${order['id']}</a></td>
									<td align="center">${order['cabPlate']}</td>
									<td align="center"><fmt:formatDate
											value="${order['pickupTime']}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
									<td align="center">${order['passengerNum']}</td>
									<td align="center"><fmt:formatDate
											value="${order['updateTime']}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
									<td align="center">${order['statusName']}</td>
									<td><c:if test="${order['statusCode'] == 1}">
											<a class="btn btn-primary btn-small" role="button" href="#"
												onClick="editPageDialog(${order['id']})"
												data-toggle="tooltip" data-placement="bottom"
												title="Click to edit the order">edit</a>
										</c:if> <c:if test="${order['statusCode'] != 2}">
											<a class="btn btn-secondary btn-small" role="button" href="#"
												onClick="deleteOneOrder(${order['id']})">delete</a>
										</c:if></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</c:if>
	</div>
</div>