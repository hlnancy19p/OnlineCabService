package domain;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Role {

	private int roleId;
	private String roleName;
	
//	private List<User> users;
	
	@Id
	public int getRoleId() {
		return roleId;
	}
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
//	@OneToMany(targetEntity=User.class, mappedBy="role_id", cascade=CascadeType.ALL,fetch=FetchType.EAGER)
//	public List<User> getUsers() {
//		return users;
//	}
//	public void setUsers(List<User> users) {
//		this.users = users;
//	}
	
}
