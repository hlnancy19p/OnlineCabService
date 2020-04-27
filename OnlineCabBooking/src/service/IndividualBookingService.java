package service;

import java.util.List;
import java.util.Map;

import org.springframework.security.access.annotation.Secured;

public interface IndividualBookingService {

	/**
	 * Create a new order record
	 * 
	 * @param map
	 *            - A Map instance which contains the necessary fields for creating
	 *            an order record
	 * @return true, if the order is created successfully; otherwise, return false
	 */
	@Secured("Passenger")
	public Boolean createOrder(Map<String, String> map);

	/**
	 * Get the information of one order
	 * 
	 * @param orderid
	 *            - the id of the order
	 * @return the detail of the order, information is stored in a Map instance
	 */
	@Secured("Passenger")
	public Map<String, Object> readOneOrder(int orderid);

	/**
	 * Update a order with given information
	 * 
	 * @param map
	 *            - A Map instance which contains the updated fields of an order
	 *            record
	 * @return true, if the order is updated; otherwise, return false;
	 */
	@Secured("Passenger")
	public Boolean updateOrder(Map<String, String> map);

	/**
	 * Delete one order
	 * 
	 * @param orderid
	 *            - the id of the order to be deleted
	 * @return true, if the order is deleted; otherwise, return false;
	 */
	@Secured("Passenger")
	public Boolean deleteOneOrder(int orderid);

	/**
	 * Delete multiple orders
	 * 
	 * @param orderIds
	 *            - the array of order IDs
	 * @return the result code for the delete operation
	 */
	@Secured("Passenger")
	public int deleteOrders(String[] orderIds);

	/**
	 * Get all individual orders of a user
	 * 
	 * @param userId
	 *            - id of the user
	 * @return a list of Map which contains information of orders related to the
	 *         user
	 */
	@Secured("Passenger")
	public List<Map<String, Object>> getOrdersInfo(int userId);
	
	/**
	 * Get all business orders of a user
	 * 
	 * @param userId
	 *            - id of the user
	 * @return a list of Map which contains information of orders related to the
	 *         user
	 */
	@Secured({"Passenger", "Business"})
	public List<Map<String, Object>> getBusinessOrdersInfo(int userId);
}
