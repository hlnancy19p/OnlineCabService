package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import domain.Order;
import service.IndividualBookingService;
import util.MapUtil;
import util.SessionCheck;

@Controller
@RequestMapping("/passenger")
public class IndividualBookingController {

	private String message;
	@Autowired
	private IndividualBookingService bookingOrderService;

	public IndividualBookingService getBookingOrderService() {
		return bookingOrderService;
	}

	public void setBookingOrderService(IndividualBookingService bookingOrderService) {
		this.bookingOrderService = bookingOrderService;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Main page for Booking Order User can view all orders to check status and do
	 * operations
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/viewOrders.htm")
	public String viewOrders(Model model, HttpServletRequest request, HttpServletResponse response) {

		// check whether session exceed time limit
		boolean isLogin = SessionCheck.checkLoginSession(request);
		if (isLogin == false) {
			// when login fail, go to fail.jsp
			this.message = "Sorry, Please Login!";
			model.addAttribute("failMessage", message);
			return "fail";
		}
		// show order list
		getOrdersList(model, request, response);

		return "passenger/viewOrdersPage";

	}

	/**
	 * Only show list of orders, which is a part of viewOrdersPage.jsp
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/ordersList.htm")
	public String getOrdersList(Model model, HttpServletRequest request, HttpServletResponse response) {

		// check whether session exceed time limit
		boolean isLogin = SessionCheck.checkLoginSession(request);
		if (isLogin == false) {
			// when login fail, go to fail.jsp
			this.message = "Sorry, Please Login!";
			model.addAttribute("failMessage", message);
			return "fail";
		}
		//retrieve the orders by userId
		HttpSession session = request.getSession();
		int userid = (int) session.getAttribute("userId");
		
		List<Map<String, Object>> orders = bookingOrderService.getOrdersInfo(userid);
		List<Map<String, Object>> businessOrders = bookingOrderService.getBusinessOrdersInfo(userid);

		model.addAttribute("ordersList", orders);
		model.addAttribute("businessOrders", businessOrders);

		return "passenger/ordersList";

	}

	/**
	 * View all the information in one order. This page can be shown dynamically by
	 * ajax in a pop dialog.
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @param orderid
	 * @return
	 */
	@RequestMapping(value = "viewOneOrder.htm", method = RequestMethod.POST)
	public String viewOneOrder(Model model, HttpServletRequest request, HttpServletResponse response) {

		// check whether session exceed time limit
		boolean isLogin = SessionCheck.checkLoginSession(request);
		if (isLogin == false) {
			// when login fail, go to fail.jsp
			this.message = "Sorry, Please Login!";
			model.addAttribute("failMessage", message);
			return "fail";
		}

		String orderIdStr = request.getParameter("orderId");

		if (orderIdStr != null) {
			// if the order id exists
			int orderId = Integer.parseInt(orderIdStr);
			Map<String, Object> orderInfo = bookingOrderService.readOneOrder(orderId);
			if(orderInfo!=null) {
				model.addAttribute("orderInfo", orderInfo);
				model.addAttribute("deletedMessage", null);
			}
			else {
				model.addAttribute("orderInfo", null);
				model.addAttribute("deletedMessage", "This order is not existed, please refresh the page!");
			}
		} else {
			model.addAttribute("orderInfo", null);
			model.addAttribute("deletedMessage", null);
		}

		return "passenger/viewOneOrderPage";

	}

	/**
	 * Get the edit form to create or update orders This page can be shown
	 * dynamically by ajax in a pop dialog.
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @param orderid
	 * @return
	 */
	@RequestMapping(value = "editOrder.htm", method = RequestMethod.POST)
	public String editOrder(Model model, HttpServletRequest request, HttpServletResponse response,
			@RequestParam(value = "orderId", required = false) String orderid) {

		// check whether session exceed time limit
		boolean isLogin = SessionCheck.checkLoginSession(request);
		if (isLogin == false) {
			this.message = "Sorry, Please Login!";
			model.addAttribute("failMessage", message);
			return "fail";
		}

		if (orderid != null) {
			// if the order id exists
			int orderId = Integer.parseInt(orderid);
			Map<String, Object> orderInfo = bookingOrderService.readOneOrder(orderId);
			if(orderInfo!=null) {
				model.addAttribute("orderInfo", orderInfo);
				model.addAttribute("deletedMessage", null);
			}
			else {
				model.addAttribute("orderInfo", null);
				model.addAttribute("deletedMessage", "This order is not existed, please refresh the page!");
			}
		} else {
			model.addAttribute("orderInfo", null);
			model.addAttribute("deletedMessage", null);
		}

		return "passenger/editOrderPage";

	}

	/**
	 * Insert new order into data base. Return message page to show whether it
	 * inserts successfully. Message page is a part of viewOrdersPage.jsp
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "createOrder.htm", method = RequestMethod.POST)
	public String createOrder(Model model, HttpServletRequest request, HttpServletResponse response) {

		// check whether session exceed time limit
		boolean isLogin = SessionCheck.checkLoginSession(request);
		if (isLogin == false) {
			// when login fail, go to fail.jsp
			this.message = "Sorry, Please Login!";
			model.addAttribute("failMessage", message);
			return "fail";
		}

		Map<String, String> map = new HashMap<String, String>();

		map = MapUtil.generateOrderMap(request);
		Boolean result = bookingOrderService.createOrder(map);

		if (result == true) {
			this.message = "create Success!";
		} else {
			this.message = "create Fail!";
		}
		model.addAttribute("message", message);

		return "passenger/messagePage";

	}

	/**
	 * Update an existing order in data base. Return message page to show whether it
	 * updates successfully. Message page is a part of viewOrdersPage.jsp
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "updateOrder.htm", method = RequestMethod.POST)
	public String updateOrder(Model model, HttpServletRequest request, HttpServletResponse response) {

		// check whether session exceed time limit
		boolean isLogin = SessionCheck.checkLoginSession(request);
		if (isLogin == false) {
			// when login fail, go to fail.jsp
			this.message = "Sorry, Please Login!";
			model.addAttribute("failMessage", message);
			return "fail";
		}

		Map<String, String> map = new HashMap<String, String>();

		map = MapUtil.generateOrderMap(request);
		Boolean result = bookingOrderService.updateOrder(map);

		if (result == true) {
			this.message = "update Success!";
		} else {
			this.message = "update Fail!";
		}
		model.addAttribute("message", message);

		return "passenger/messagePage";
	}

	/**
	 * Delete one order from data base. Refresh the page to viewOrdersPage.jsp
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @param orderid
	 * @return
	 */
	@RequestMapping(value = "deleteOneOrder.htm", method = RequestMethod.POST)
	public String deleteOneOrder(Model model, HttpServletRequest request, HttpServletResponse response) {

		// check whether session exceed time limit
		boolean isLogin = SessionCheck.checkLoginSession(request);
		if (isLogin == false) {
			// when login fail, go to fail.jsp
			this.message = "Sorry, Please Login!";
			model.addAttribute("failMessage", message);
			return "fail";
		}

		String orderId = request.getParameter("orderId");

		Boolean result = bookingOrderService.deleteOneOrder(Integer.parseInt(orderId));

		if (result == true) {
			this.message = "delete one order Success!";
		} else {
			this.message = "delete one order Fail!";
		}
		model.addAttribute("message", message);

		return "passenger/messagePage";
	}

	/**
	 * Delete some orders from data base. Return message page to show whether it
	 * deletes successfully. Message page is a part of viewOrdersPage.jsp
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "deleteOrders.htm", method = RequestMethod.POST)
	public String deleteOrders(Model model, HttpServletRequest request, HttpServletResponse response) {

		// check whether session exceed time limit
		boolean isLogin = SessionCheck.checkLoginSession(request);
		if (isLogin == false) {
			// when login fail, go to fail.jsp
			this.message = "Sorry, Please Login!";
			model.addAttribute("failMessage", message);
			return "fail";
		}

		String orders = request.getParameter("orders");
		String[] orderIds = orders.split(",");

		int result = bookingOrderService.deleteOrders(orderIds);

		if (result > 0) {
			this.message = "delete " + result + " orders Success!";
		} else {
			this.message = "delete orders Fail!";
		}
		model.addAttribute("message", message);

		return "passenger/messagePage";
	}
	
	
}
