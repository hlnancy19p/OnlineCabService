package domain;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

@Entity
public class Order {
	final private String MSG_NO_CAB_INFO = "To be allocated";
	final private String MSG_NO_USER_INFO = "User info not found";
	// attribute of an order
	private int orderId;
	private Timestamp updateTime;
	private String pickupAddr;
	private Timestamp pickupTime;
	private String payment;
	// private Integer cabId;
	// private Integer userId;
	private String orderNotes;
	private String contactPhone;
	private int numOfPassengers;
	// private Integer orderStatus;
	private int version = 0;

	private Cab cab;
	private User passenger;
	private Status status;
	private User creater;

	@Id
	@GeneratedValue
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public String getPickupAddr() {
		return pickupAddr;
	}

	public void setPickupAddr(String pickupAddr) {
		this.pickupAddr = pickupAddr;
	}

	public Timestamp getPickupTime() {
		return pickupTime;
	}

	public void setPickupTime(Timestamp pickupTime) {
		this.pickupTime = pickupTime;
	}

	public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	// public Integer getCabId() {
	// return cabId;
	// }
	// public void setCabId(Integer cabId) {
	// this.cabId = cabId;
	// }
	// public Integer getUserId() {
	// return userId;
	// }
	// public void setUserId(Integer userId) {
	// this.userId = userId;
	// }
	public String getOrderNotes() {
		return orderNotes;
	}

	public void setOrderNotes(String orderNotes) {
		this.orderNotes = orderNotes;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public int getNumOfPassengers() {
		return numOfPassengers;
	}

	public void setNumOfPassengers(int numOfPassengers) {
		this.numOfPassengers = numOfPassengers;
	}

	/**
	 * get the name of the applier
	 * 
	 * @return
	 */
	public String getApplierName() {
		if (passenger != null) {
			return passenger.getFullName();
		} else {
			return MSG_NO_USER_INFO;
		}

	}
	
	/**
	 * get the name of the creater
	 * 
	 * @return
	 */
	public String getCreaterName() {
		if (creater != null) {
			return creater.getFullName();
		} else {
			return MSG_NO_USER_INFO;
		}

	}

	/**
	 * get the plate number of the allocated cab
	 * 
	 * @return If cab information can be retrieved, return plate number; otherwise,
	 *         return NO_DATA_MSG
	 */
	public String getCabPlateNumber() {
		if (cab != null) {
//			System.out.println("Cab Id: " + cab.getCabId());
//			System.out.println("Cab Plate Number: " + cab.getPlateNum());
			return cab.getPlateNum();
		} else {
//			System.out.println("No cab retrieved");
			return MSG_NO_CAB_INFO;
		}

	}
	// public Integer getOrderStatus() {
	// return orderStatus;
	// }
	// public void setOrderStatus(Integer orderStatus) {
	// this.orderStatus = orderStatus;
	// }

	/**
	 * Get the detail of an order
	 * 
	 * @return Information stored in Map<String, Object>
	 */
	public Map<String, Object> getDetail() {
		Map<String, Object> info = new HashMap<String, Object>();
		info.put("id", this.getOrderId());
		info.put("applier", this.getApplierName());// applier to use the cab
		info.put("creater", this.getCreaterName());// creater of an order
		info.put("createrId", this.creater.getUserId());// creater of an order
		info.put("cabPlate", this.getCabPlateNumber());
		info.put("pickupAddr", this.getPickupAddr());
		info.put("pickupTime", this.getPickupTime());
		info.put("contactNumber", this.getContactPhone());
		info.put("passengerNum", this.getNumOfPassengers());
		info.put("payment", this.getPayment());
		info.put("orderNotes", this.getOrderNotes());
		info.put("updateTime", this.getUpdateTime());
		info.put("version", this.getVersion());
		info.put("statusCode", this.getStatus().getStatusId());
		info.put("statusName", this.getStatus().getStatusName());
		return info;
	}

	@ManyToOne
	@JoinColumn(name = "status_id")
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@ManyToOne
	@JoinColumn(name = "cab_id")
	public Cab getCab() {
		return cab;
	}

	public void setCab(Cab cab) {
		this.cab = cab;
	}

	@ManyToOne
	@JoinColumn(name = "passenger_id")
	public User getPassenger() {
		return passenger;
	}
	
	public void setPassenger(User passenger) {
		this.passenger = passenger;
	}

	@Version
	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	@ManyToOne
	@JoinColumn(name = "passenger_id")
	public User getCreater() {
		return creater;
	}

	public void setCreater(User creater) {
		this.creater = creater;
	}

}
