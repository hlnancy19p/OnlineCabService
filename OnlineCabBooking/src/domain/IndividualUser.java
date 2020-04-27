package domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

/**
 * Domain class which represents the individual users. An individual user can
 * manage its personal booking order.
 * 
 * If the user has been registered as a company's employee, it will also has
 * records of personal business orders.
 * 
 * @author jiawen
 *
 */
public class IndividualUser extends User {
	// set of personal created orders
	Set<Order> personalOrders;
	private String roleName = "Passenger";
	
	public void setPersonalOrders(Set<Order> personalOrders) {
		this.personalOrders = personalOrders;
	}
	// set of all orders related to the user
	Set<Order> allOrders;
	
	/**
	 * Get personal orders of an individual user
	 * 
	 * @return a set of Order objects
	 */
	@OneToMany(targetEntity=Order.class, mappedBy="creater", cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	public Set<Order> getPersonalOrders() {
		super.getOrders();
		return personalOrders; 
	}

	//TODO add mapping
	@OneToMany(targetEntity=Order.class, mappedBy="passenger", cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	public Set<Order> getAllOrders(){
		return allOrders;
	}
	

	public void setAllOrders(Set<Order> orders) {
		this.allOrders = orders;
	}
	/**
	 * 
	 * Get business orders created for an individual user
	 * @return
	 */
	public Set<Order> getBusinessOrders() {
		//TODO compare super.getId() with the 'passenger' field in Order table
		// copy all orders
		Set<Order> businessOrders = new HashSet<>(getAllOrders());
		// filter personal orders
		businessOrders.removeAll(getPersonalOrders());
		return businessOrders;
	}
	
	public Role getRole() {
		Role role = new Role();
		role.setRoleId(2);
		role.setRoleName(roleName);
		return role;
	}

}
