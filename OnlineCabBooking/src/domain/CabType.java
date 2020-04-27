package domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CabType {

	private int typeId;
	private int seatsNum;
	private String cabSize;
	private String cabColor;
	
	@Id
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}
	public int getSeatsNum() {
		return seatsNum;
	}
	public void setSeatsNum(int seatsNum) {
		this.seatsNum = seatsNum;
	}
	public String getCabSize() {
		return cabSize;
	}
	public void setCabSize(String cabSize) {
		this.cabSize = cabSize;
	}
	public String getCabColor() {
		return cabColor;
	}
	public void setCabColor(String cabColor) {
		this.cabColor = cabColor;
	}
	
	
}
