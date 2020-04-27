package domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class User {

	private int userId;
	private String userName;
	private String fullName;
	private String passWord;
	private String userAddr;
	private String contactPhone;
//	private int roleId;
	private String companyNumber;
	
	private Role role;
	private Company company;
	// all orders created by the user
	private Set<Order> orders;
	
	
	/**
	 * Get the detail of an order
	 * 
	 * @return Information stored in Map<String, Object>
	 */
	public Map<String, Object> getDetail() {
		Map<String, Object> info = new HashMap<String, Object>();
		info.put("id", this.getUserId());
		info.put("name", this.getUserName());
		info.put("fullname", this.getFullName());
		info.put("companynumber", this.getCompanyNumber());
//		info.put("company", this.getCompany().getCompanyName());
//		info.put("companyid", this.getCompany().getCompanyId());
//		info.put("role", this.getRole().getRoleName());
//		info.put("address", this.getUserAddr());
//		info.put("phone", this.getContactPhone());
		return info;
	}
	
	@Id
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public String getUserAddr() {
		return userAddr;
	}
	public void setUserAddr(String userAddr) {
		this.userAddr = userAddr;
	}
	public String getContactPhone() {
		return contactPhone;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
//	public int getRoleId() {
//		return roleId;
//	}
//	public void setRoleId(int roleId) {
//		this.roleId = roleId;
//	}
//	
	@ManyToOne
	@JoinColumn(name="role_id")
	public Role getRole() {
		if(role == null)
			role = new Role();
			role.setRoleId(0);
			role.setRoleName("Administrator");
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	@OneToMany(targetEntity=Order.class, mappedBy="creater", cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	public Set<Order> getOrders() {
		return orders;
	}
	
	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
	
	@ManyToOne
	@JoinColumn(name="company_id")
	public Company getCompany() {
		return company;
	}
	public void setCompany(Company company) {
		this.company = company;
	}
	
	public String getCompanyNumber() {
		return companyNumber;
	}
	public void setCompanyNumber(String companyNumber) {
		this.companyNumber = companyNumber;
	}
	
	
	
}
