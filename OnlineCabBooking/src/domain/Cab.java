package domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Cab {

	private int cabId;
	private String plateNum;
	private String cabInfo;
	private CabType cabType;
	
	@Id
	public int getCabId() {
		return cabId;
	}
	public void setCabId(int cabId) {
		this.cabId = cabId;
	}
	public String getPlateNum() {
		return plateNum;
	}
	public void setPlateNum(String plateNum) {
		this.plateNum = plateNum;
	}
	public String getCabInfo() {
		return cabInfo;
	}
	public void setCabInfo(String cabInfo) {
		this.cabInfo = cabInfo;
	}
	
	@ManyToOne
	@JoinColumn(name="type_id")
	public CabType getCabType() {
		return cabType;
	}
	public void setCabType(CabType cabType) {
		this.cabType = cabType;
	}
	
	
}
