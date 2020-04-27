
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<c:if test="${not empty deletedMessage}">
	${deletedMessage }	
</c:if>
<c:if test="${empty deletedMessage }">
<c:if test="${empty orderInfo}">
Create an order<br>
  pickup Time: <input id="pt" type="text" name="pickupTime">
	<span id="pt_msg" style="color: #ff0000"></span>
	<br>
  pickup Address: <input id="pa" type="text" name="pickupAddr">
	<span id="pa_msg" style="color: #ff0000"></span>
	<br>
  number Of Passengers: <input id="nop" type="text"
		name="numOfPassengers">
	<span id="nop_msg" style="color: #ff0000"></span>
	<br>
  contact Phone: <input id="cp" type="text" name="contactPhone">
	<span id="cp_msg" style="color: #ff0000"></span>
	<br>
  order Notes: <input id="on" type="text" name="orderNotes">
	<br>

	<a href="#" onClick="SubmitOrder(0)">Submit</a>
	<br>

</c:if>
<c:if test="${not empty orderInfo}">
	<form>
		<fieldset>
			<legend> Edit Order </legend>
			<br /> Order ID : ${orderInfo['id']}<br /> <input id="oid"
				type="text" name="orderId" value="${orderInfo['id']}"
				style="display: none"> <br /> pickup Time: <input id="pt"
				type="text" name="pickupTime"
				value="<fmt:formatDate value="${orderInfo['pickupTime']}" pattern="yyyy-MM-dd HH:mm:ss" />">
			<span id="pt_msg" style="color: #ff0000"></span> <br> pickup
			Address: <input id="pa" type="text" name="pickupAddr"
				value="${orderInfo['pickupAddr']}"> <span id="pa_msg"
				style="color: #ff0000"></span> <br> number Of Passengers: <input
				id="nop" type="text" name="numOfPassengers"
				value="${orderInfo['passengerNum']}"> <span id="nop_msg"
				style="color: #ff0000"></span> <br> contact Phone: <input
				id="cp" type="text" name="contactPhone"
				value="${orderInfo['contactNumber']}"> <span id="cp_msg"
				style="color: #ff0000"></span> <br> order Notes: <input id="on"
				type="text" name="orderNotes" value="${orderInfo['orderNotes']}">
			<br> 
			<input id="status" type="text" name="statusId"
				value="${orderInfo['statusCode']}" style="display: none">
			<input id="version" type="text" name="version"
				value="${orderInfo['version']}" style="display: none"> <input
				id="creater" type="text" name="creater"
				value="${orderInfo['createrId']}" style="display: none">
				 <a
				href="#" onClick="SubmitOrder(${oneOrder['id']})">Submit</a> <br>
		</fieldset>
	</form>
</c:if>
</c:if>
