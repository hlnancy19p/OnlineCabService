package mapper;

import java.util.List;

import domain.Company;
import domain.Order;
import domain.User;

public interface BusinessBookingMapper {

	/**
	 * Add an order record to the database
	 * 
	 * @param order
	 *            - the instance of an Order object to be stored
	 * @return true, if the order record is stored successfully; otherwise, return
	 *         false
	 */
	public Boolean createOrder(Order order);

	/**
	 * Get the information of an order from database
	 * 
	 * @param order
	 *            - the instance of an order with id information included
	 * @return An instance of Order object which contains full information of a
	 *         specific order
	 */
	public Order readOneOrder(Order order);

	/**
	 * Update the information of an order in the database
	 * 
	 * @param order
	 *            - the instance of an Order object
	 * @return true, if the information is updated successfully; otherwise, return
	 *         false
	 */
	public Boolean updateOrder(Order order);

	/**
	 * Delete one order record in the database
	 * 
	 * @param order
	 *            - an order object which represents the order record to be deleted
	 *            in the database
	 * @return true, if the corresponding order record is deleted successfully;
	 *         otherwise, return false
	 */
	public Boolean deleteOneOrder(Order order);

	/**
	 * Delete multiple order records in the database
	 * 
	 * @param orderIds
	 *            - a list of IDs for order records to be deleted
	 * @return the result code for delete operation
	 */
	public int deleteOrders(int[] orderIds);

	/**
	 * Get all orders related to a specific user
	 * 
	 * @param user
	 *            - the user (employee) to be retrieved related order records
	 *        company
	 *        	  - the company for the business user
	 * @return a list of orders related to the specified user
	 */
	public List<Order> getEmployeeOrders(User user,Company company);
	
	/**
	 * Get all orders related to a specific company
	 * 
	 * @param company
	 *            - the company to be retrieved related order records
	 * @return a list of orders related to the specified company
	 */
	public List<Order> getCompanyOrders(Company company);

	/**
	 * Get all employees related to a specific company
	 * 
	 * @param company
	 *            - the company to be retrieved related order records
	 * @return a list of orders related to the specified company
	 */
	public List<User> getCompanyEmployees(Company company);


}
