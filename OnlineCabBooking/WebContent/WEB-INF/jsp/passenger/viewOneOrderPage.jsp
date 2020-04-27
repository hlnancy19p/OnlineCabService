<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:if test="${not empty deletedMessage}">
	${deletedMessage }	
</c:if>
<c:if test="${empty deletedMessage}">
<fieldset>
	<legend>Order Information</legend>
	<table>
		<tr>
			<td align="center">orderId</td>
			<td align="center">${orderInfo['id']}</td>
		</tr>
		<tr>
			<td align="center">applier</td>
			<td align="center">${orderInfo['applier']}</td>
		</tr>

		<tr>
			<td align="center">cab</td>
			<td align="center">${orderInfo['cabPlate']}</td>
		</tr>


		<tr>
			<td align="center">pickupAddr</td>
			<td align="center">${orderInfo['pickupAddr']}</td>
		</tr>
		<tr>
			<td align="center">pickupTime</td>
			<td align="center"><fmt:formatDate
					value="${orderInfo['pickupTime']}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
		</tr>
		<tr>
			<td align="center">contactPhone</td>
			<td align="center">${orderInfo['contactNumber']}</td>
		</tr>
		<tr>
			<td align="center">numOfPassengers</td>
			<td align="center">${orderInfo['passengerNum']}</td>
		</tr>
		<tr>
			<td align="center">payment method</td>
			<td align="center">${orderInfo['payment']}</td>
		</tr>
		<tr>
			<td align="center">orderNotes</td>
			<td align="center">${orderInfo['orderNotes']}</td>
		</tr>
		<tr>
			<td align="center">updateTime</td>
			<td align="center"><fmt:formatDate
					value="${orderInfo['updateTime']}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
		</tr>
		<tr>
			<td align="center">creater</td>
			<td align="center">${orderInfo['creater']}</td>
		</tr>
		<tr>
			<td align="center">Status</td>
			<td align="center">${orderInfo['statusName']}</td>
		</tr>
	</table>
</fieldset>
</c:if>