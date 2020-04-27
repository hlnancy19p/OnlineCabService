package domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Status {

	private int statusId;
	private String statusName;
	
	@Id
	public int getStatusId() {
		return statusId;
	}
	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}
	
	public String getStatusName() {
		return statusName;
	}
	
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
}
