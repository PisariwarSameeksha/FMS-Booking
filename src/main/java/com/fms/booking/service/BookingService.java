package com.fms.booking.service;

import java.math.BigInteger;
import java.util.List;

import com.fms.booking.entity.Booking;
import com.fms.booking.entity.Passenger;
import com.fms.booking.exception.BookingException;
import com.fms.booking.exception.PassengerException;

public interface BookingService {
	
	public Booking  addBooking(Booking booking);
	
	public Booking modifyBooking(Booking booking) throws BookingException;
	public Booking cancelBooking(long bookingId) throws BookingException;
	public Booking cancelTicketFromBooking(Booking booking, long passengerId,double price) throws  PassengerException, BookingException;

	public List<Booking> viewAllBookingsOfUser(BigInteger userId) throws BookingException;
	public List<Booking> viewAllBookings() throws BookingException;
	public Booking validateBooking(long bookingId) throws BookingException;
	public Passenger validatePassenger(long passengerId) throws PassengerException;
	
	public String deleteBooking(long bookingId) throws BookingException; 
}
