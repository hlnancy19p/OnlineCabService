package service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Company;
import domain.Order;
import domain.Status;
import domain.User;
import mapper.BusinessBookingMapper;
import service.BusinessBookingService;
import util.Params;

@Service("businessBookingService")
public class BusinessBookingServiceImpl implements BusinessBookingService {

	@Autowired
	private BusinessBookingMapper businessDataMapper;

	public BusinessBookingMapper getBusinessBooing() {
		return businessDataMapper;
	}

	public void setBusinessBooking(BusinessBookingMapper businessDataMapper) {
		this.businessDataMapper = businessDataMapper;
	}

	// check whether user create one order successfully
	@Override
	public Boolean createOrder(Map<String, String> map) {

		Order order = new Order();
		order.setPickupAddr(map.get("pickupAddr"));
		order.setPickupTime(Timestamp.valueOf(map.get("pickupTime")));
		order.setContactPhone(map.get("contactPhone"));
		order.setOrderNotes(map.get("orderNotes"));
		User applier = new User();
		applier.setUserId(Integer.parseInt(map.get("userId")));
		order.setPassenger(applier);
		User creater = new User();
		creater.setUserId(Integer.parseInt(map.get("createrId")));
		order.setCreater(creater);
		order.setNumOfPassengers(Integer.parseInt(map.get("numOfPassengers")));
		Status status = new Status();
		status.setStatusId(Params.ORDER_INITIAL_STATUS);
		order.setStatus(status);

		Boolean result = businessDataMapper.createOrder(order);

		return result;
	}

	// read info of one order
	public Map<String, Object> readOneOrder(int orderid) {

		Order order = new Order();

		order.setOrderId(orderid);

		Order order_fullInfo = businessDataMapper.readOneOrder(order);
		if(order_fullInfo!=null) {
		Map<String, Object> info = order_fullInfo.getDetail();
//		System.out.println("*********" + info.get("id"));
		return info;
		}
		else
			return null;
	}

	// update one order
	@Override
	public Boolean updateOrder(Map<String, String> map) {

		Order order = new Order();
		order.setOrderId(Integer.parseInt(map.get("orderId")));
		order.setPickupAddr(map.get("pickupAddr"));
		order.setPickupTime(Timestamp.valueOf(map.get("pickupTime")));
		order.setContactPhone(map.get("contactPhone"));
		order.setOrderNotes(map.get("orderNotes"));
		order.setNumOfPassengers(Integer.parseInt(map.get("numOfPassengers")));
		order.setVersion(Integer.parseInt(map.get("version")));
		Status status = new Status();
		status.setStatusId(Integer.parseInt(map.get("statusId")));
		order.setStatus(status);
		User applier = new User();
		applier.setUserId(Integer.parseInt(map.get("userId")));
		order.setPassenger(applier);
		User creater = new User();
		creater.setUserId(Integer.parseInt(map.get("createrId")));
		order.setCreater(creater);
		
		Boolean result = businessDataMapper.updateOrder(order);

		return result;
	}

	// delete one order
	@Override
	public Boolean deleteOneOrder(int orderId) {

		Order order = new Order();
		order.setOrderId(orderId);
		Boolean result = businessDataMapper.deleteOneOrder(order);

		return result;
	}

	// delete orders
	@Override
	public int deleteOrders(String[] orderIds) {

		int[] orderids = new int[orderIds.length];
		for (int i = 0; i < orderIds.length; i++) {
			orderids[i] = Integer.parseInt(orderIds[i]);
		}

		int result = businessDataMapper.deleteOrders(orderids);

		return result;
	}

	@Override
	public List<Map<String, Object>> getEmployeeOrdersInfo(int userId, int companyId) {
		// TODO Auto-generated method stub
		// retrieve the User object by userId
		User user = new User();
		user.setUserId(userId);
		// retrieve the Company object by companyId
		Company company = new Company();
		company.setCompanyId(companyId);
		// get the list of orders
		List<Order> orders = businessDataMapper.getEmployeeOrders(user, company);
		// retrieve the information
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		if(orders == null) {
			return null;
		}
		for (Order order : orders) {
			result.add(order.getDetail());
		}

		return result;
	}

	@Override
	public List<Map<String, Object>> getCompanyOrdersInfo(int companyId) {
		// TODO Auto-generated method stub
		// retrieve the Company object by companyId
		Company company = new Company();
		company.setCompanyId(companyId);
		// get the list of orders
		List<Order> orders = businessDataMapper.getCompanyOrders(company);
		// retrieve the information
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		if(orders == null) {
			return null;
		}
		for (Order order : orders) {
			result.add(order.getDetail());
		}

		return result;
	}

	@Override
	public List<Map<String, Object>> getCompanyEmployeesInfo(int companyId) {
		// TODO Auto-generated method stub
		// retrieve the Company object by companyId
		Company company = new Company();
		company.setCompanyId(companyId);
		// get the list of employees
		List<User> employees = businessDataMapper.getCompanyEmployees(company);
		// retrieve the information
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		if(employees == null) {
			return null;
		}
		for (User employee : employees) {
			result.add(employee.getDetail());
		}
		return result;
	}



}
