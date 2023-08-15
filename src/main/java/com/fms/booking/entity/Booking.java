package com.fms.booking.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

@Entity
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long bookingId;

	private long userId;

//	@NotNull(message="Booking must contain atleast one passenger")
	@Min(value = 1, message = "Value must be at least 1")
	@Max(value = 10, message = "Value must be at most 10")
	private Integer passengerCount;

	@PastOrPresent(message = "must be a date in the past or in the present")
	private LocalDate bookingDate;

	@Min(value = 3000, message = "Value must be at least 3000")
	@Max(value = 2000000, message = "Value must be at most 2000000")
	private Double ticketCost;

	@Pattern(regexp = "^[789]\\d{9}$", message = "Phone number must be 10 digits")
	private String contactNo;

//	@NotNull(message="Only CANCELLED or BOOKED are allowed")
	private BookingStatus bookingStatus;

	private long sheduleId;

	@ManyToMany(fetch = FetchType.LAZY)
	List<Passenger> passengerList = new ArrayList<>();

	public Booking() {
		super();

	}



	public Booking(long bookingId, long userId,
			@Min(value = 1, message = "Value must be at least 1") @Max(value = 10, message = "Value must be at most 10") Integer passengerCount,
			@PastOrPresent(message = "must be a date in the past or in the present") LocalDate bookingDate,
			@Min(value = 3000, message = "Value must be at least 3000") @Max(value = 2000000, message = "Value must be at most 2000000") Double ticketCost,
			@Pattern(regexp = "^[789]\\d{9}$", message = "Phone number must be 10 digits") String contactNo,
			BookingStatus bookingStatus, List<Passenger> passengerList, long sheduleId) {
		super();
		this.bookingId = bookingId;
		this.userId = userId;
		this.passengerCount = passengerCount;
		this.bookingDate = bookingDate;
		this.ticketCost = ticketCost;
		this.contactNo = contactNo;
		this.bookingStatus = bookingStatus;
		this.passengerList = passengerList;
		this.sheduleId = sheduleId;

	}

	public String getContactNo() {
		return contactNo;
	}

	public void setContactNo(String contactNo) {
		this.contactNo = contactNo;
	}

	public long getBookingId() {
		return bookingId;
	}

	public void setBookingId(long bookingId) {
		this.bookingId = bookingId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Integer getPassengerCount() {
		return passengerCount;
	}

	public void setPassengerCount(Integer passengerCount) {
		this.passengerCount = passengerCount;
	}

	public LocalDate getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(LocalDate bookingDate) {
		this.bookingDate = bookingDate;
	}

	public double getTicketCost() {
		return ticketCost;
	}

	public void setTicketCost(double ticketCost) {
		this.ticketCost = ticketCost;
	}

	public List<Passenger> getPassengerList() {
		return passengerList;
	}

	public void setPassengerList(List<Passenger> passengerList) {
		this.passengerList = passengerList;
	}

	public BookingStatus getBookingStatus() {
		return bookingStatus;
	}

	public void setBookingStatus(BookingStatus bookingStatus) {
		this.bookingStatus = bookingStatus;
	}

	public enum BookingStatus {
		CANCELLED, BOOKED
	}

	public long getSheduleId() {
		return sheduleId;
	}

	public void setSheduleId(long sheduleId) {
		this.sheduleId = sheduleId;
	}

}
