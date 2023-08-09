package com.fms.booking.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class Passenger {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long passengerId;
	
	@NotBlank(message = "Name is mandatory")
	@Pattern(regexp="^[A-Za-z\\s]{3,15}$")
	private String passengerName;
	
	@NotNull(message = "contact number must not be null")
	@Pattern(regexp = "^[789]\\d{9}$", message = "Phone number must be 10 digits")
	private String contactNo;
	
	@Pattern(regexp = "^[a-zA-Z0-9]{7,12}$", message = "Only alphanumerics values are allowed")
	@NotNull(message = "uid must not be null")
	private String uid;
	
	@NotNull(message = "Age must not be null")
	@Min(value = 1, message = "Value must be at least 1")
    @Max(value = 120, message = "Value must be at most 120")
	private Integer passengerAge;
	
	@Pattern(regexp="^(Male|Female|M|F)$",message="Only Male / Female are allowed")
	@NotBlank(message = "Gender is mandatory")
	private String gender;
	
	
	@Min(value = 0, message = "Value must be at least 1")
    @Max(value = 50, message = "Value must be at most 120")
	private double lugguage;
	
	public Passenger() {
		super();
	}



	public Passenger(long passengerId, String passengerName, String contactNo, String uid, Integer passengerAge,
			String gender, double lugguage) {
		super();
		this.passengerId = passengerId;
		this.passengerName = passengerName;
		this.contactNo = contactNo;
		this.uid = uid;
		this.passengerAge = passengerAge;
		this.gender = gender;
		this.lugguage = lugguage;
	}

	public double getLugguage() {
		return lugguage;
	}


	public void setLugguage(double lugguage) {
		this.lugguage = lugguage;
	}

	public String getContactNo() {
		return contactNo;
	}


	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}


	public String getUid() {
		return uid;
	}


	public void setUid(String uid) {
		this.uid = uid;
	}


	public long getPassengerId() {
		return passengerId;
	}
	public void setPassengerId(long passengerId) {
		this.passengerId = passengerId;
	}
	public Integer getPassengerAge() {
		return passengerAge;
	}
	public void setPassengerAge(Integer passengerAge) {
		this.passengerAge = passengerAge;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPassengerName() {
		return passengerName;
	}
	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}
	
	
	

}
