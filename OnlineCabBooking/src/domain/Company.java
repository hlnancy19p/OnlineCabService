package domain;


import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Company {

	private int companyId;
	private String companyName;
	
	private Set<User> employees;
	
	@Id
	public int getCompanyId() {
		return companyId;
	}
	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	
	@OneToMany(targetEntity=User.class, mappedBy="company", cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	public Set<User> getEmployees() {
		return employees;
	}
	public void setEmployees(Set<User> employees) {
		this.employees = employees;
	}
	
	/**
	 * Get the set of reception user accounts
	 * @return Set of users
	 */
	public Set<User> getReceptionistSet(){
		Set<User> receptionists = new HashSet<>();
		for(User employee: getEmployees()) {
			// TODO replace the "..." with receptionist role name
			if(employee.getRole().getRoleName().equals("Business")) {
				receptionists.add(employee);
			}
		}
		return receptionists;
	}
}
