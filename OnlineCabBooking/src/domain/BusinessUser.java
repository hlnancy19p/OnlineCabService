package domain;

import java.util.HashSet;
import java.util.Set;

public class BusinessUser extends User{

	private String roleName = "Business";
	
	public Set<User> getEmployees(){
		return super.getCompany().getEmployees();
	}
	
	
	
	public Set<Order> getAllCompanyOrders(){
		Set<Order> companyOrders = new HashSet<>();
		// traverse all business users in the company
		for(User receptionist: super.getCompany().getReceptionistSet()) {
			companyOrders.addAll(receptionist.getOrders());
		}
		return companyOrders;
	}
	
	public Role getRole() {
		Role role = new Role();
		role.setRoleId(4);
		role.setRoleName(roleName);
		return role;
	}

}
