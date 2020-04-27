package controller;

import java.util.ArrayList;
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
import service.BusinessBookingService;
import util.MapUtil;
import util.SessionCheck;

@Controller
@RequestMapping("/business")
public class BusinessBookingController {

	private String message;
	@Autowired
	private BusinessBookingService businessBookingService;

	public BusinessBookingService getBusinessBookingService() {
		return businessBookingService;
	}

	public void setBusinessBookingService(BusinessBookingService businessBookingService) {
		this.businessBookingService = businessBookingService;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Main page for Business Booking, Business User can view all orders in the same
	 * company and do further operations.
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/viewBusinessOrders.htm")
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
		// show all employees
		HttpSession session = request.getSession();
		int companyid = (int) session.getAttribute("companyId");
		List<Map<String, Object>> employees = businessBookingService.getCompanyEmployeesInfo(companyid);
		
		model.addAttribute("appliersList", employees);
		
		return "business/viewOrdersPage";

	}

	/**
	 * Only show list of orders, which is a part of viewOrdersPage.jsp
	 * the list of orders can be all orders in the company or the orders from
	 * one employee in the company.
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/businessOrdersList.htm")
	public String getOrdersList(Model model, HttpServletRequest request, HttpServletResponse response) {

		// check whether session exceed time limit
		boolean isLogin = SessionCheck.checkLoginSession(request);
		if (isLogin == false) {
			// when login fail, go to fail.jsp
			this.message = "Sorry, Please Login!";
			model.addAttribute("failMessage", message);
			return "fail";
		}
		List<Map<String, Object>> orders = new ArrayList<Map<String,Object>>();
		String applierMessage = null;
		//retrieve the orders by userId(employeeId) and companyId
		String employeeId = request.getParameter("userId");
		HttpSession session = request.getSession();
		int companyid = (int) session.getAttribute("companyId");
		// if business does not choose a specific employee, show all company orders.
		if (employeeId == null || employeeId.equals("0")) {
			orders = businessBookingService.getCompanyOrdersInfo(companyid);
			applierMessage = "All Employees' Order:";
		}else {
			int userid = Integer.parseInt(employeeId);
			orders = businessBookingService.getEmployeeOrdersInfo(userid, companyid);
		}
		model.addAttribute("ordersList", orders);
		model.addAttribute("applierMessage", applierMessage);

		return "business/ordersList";

	}

	/**
	 * View all the information in one order of an employee.
	 * This page can be shown dynamically by ajax in a pop dialog.
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @param orderid
	 * @return
	 */
	@RequestMapping(value = "viewOneEmployeeOrder.htm", method = RequestMethod.POST)
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
			Map<String, Object> orderInfo = businessBookingService.readOneOrder(orderId);
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

		return "business/viewOneOrderPage";

	}

	/**
	 * Get the edit form to create or update orders.
	 * This page can be shown dynamically by ajax in a pop dialog.
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @param orderid
	 * @return
	 */
	@RequestMapping(value = "editEmployeeOrder.htm", method = RequestMethod.POST)
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
			Map<String, Object> orderInfo = businessBookingService.readOneOrder(orderId);
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
		
		// show all employees
		// business can only edit for the employees in the same company.
		HttpSession session = request.getSession();
		int companyid = (int) session.getAttribute("companyId");
		List<Map<String, Object>> employees = businessBookingService.getCompanyEmployeesInfo(companyid);
		
		model.addAttribute("appliersList", employees);
		
		return "business/editOrderPage";

	}

	/**
	 * Insert new order into data base.
	 *  Return message page to show whether it inserts successfully. 
	 *  Message page is a part of viewOrdersPage.jsp
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "createEmployeeOrder.htm", method = RequestMethod.POST)
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

		map = MapUtil.generateBusinessOrderMap(request);
		Boolean result = businessBookingService.createOrder(map);

		if (result == true) {
			this.message = "create Success!";
		} else {
			this.message = "create Fail!";
		}
		model.addAttribute("message", message);

		return "business/messagePage";

	}

	/**
	 * Update an existing order in data base.
	 * Return message page to show whether it updates successfully.
	 * Message page is a part of viewOrdersPage.jsp
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "updateEmployeeOrder.htm", method = RequestMethod.POST)
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

		map = MapUtil.generateBusinessOrderMap(request);
		Boolean result = businessBookingService.updateOrder(map);

		if (result == true) {
			this.message = "update Success!";
		} else {
			this.message = "update Fail!";
		}
		model.addAttribute("message", message);

		return "business/messagePage";
	}

	/**
	 * Delete one order from data base.
	 * Return message page to show whether it deletes successfully.
	 * Message page is a part of viewOrdersPage.jsp
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @param orderid
	 * @return
	 */
	@RequestMapping(value = "deleteOneEmplyeeOrder.htm", method = RequestMethod.POST)
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

		Boolean result = businessBookingService.deleteOneOrder(Integer.parseInt(orderId));

		if (result == true) {
			this.message = "delete one order Success!";
		} else {
			this.message = "delete one order Fail!";
		}
		model.addAttribute("message", message);

		return "business/messagePage";
	}

	/**
	 * Delete some orders from data base. 
	 * Return message page to show whether it deletes successfully.
	 * Message page is a part of viewOrdersPage.jsp
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "deleteEmployeeOrders.htm", method = RequestMethod.POST)
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

		int result = businessBookingService.deleteOrders(orderIds);

		if (result > 0) {
			this.message = "delete " + result + " orders Success!";
		} else {
			this.message = "delete orders Fail!";
		}
		model.addAttribute("message", message);

		return "business/messagePage";
	}
	
	
}
