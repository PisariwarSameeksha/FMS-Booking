package com.fms.booking.controller;

import java.math.BigInteger;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fms.booking.entity.Booking;
import com.fms.booking.entity.Passenger;
import com.fms.booking.exception.BookingException;
import com.fms.booking.exception.PassengerException;
import com.fms.booking.service.BookingService;

@RestController
@EnableWebMvc
public class BookingController {
	
	@Autowired
	private BookingService bookingService;
	
	@PostMapping(value="/addBooking")
	public Booking  addBooking(@Valid @RequestBody Booking booking) {
		return this.bookingService.addBooking(booking);
	}
	
	@PutMapping("/modifyBooking/modifyPassenger/booking")
	public Booking modifyBooking(@Valid @RequestBody Booking booking) throws BookingException {
		return this.bookingService.modifyBooking(booking);
	}
	
	@PutMapping("/cancelBooking/{bookingId}")
	public Booking cancelBooking(@PathVariable long bookingId) throws BookingException {
		return this.bookingService.cancelBooking(bookingId);
	}
	@PutMapping("/cancelSingleTicket/booking/{passengerId}")
	public Booking cancelTicketFromBooking(@Valid @RequestBody Booking booking, @PathVariable long passengerId,long price) throws BookingException, PassengerException {
		return this.bookingService.cancelTicketFromBooking(booking,passengerId,price);
	}
	
	@GetMapping("/userBookings/{userId}")
	public List<Booking> viewAllBookingsOfUser(@PathVariable BigInteger userId) throws BookingException{
		return this.bookingService.viewAllBookingsOfUser(userId);
	}
	
	@GetMapping("/allBookings")
	public List<Booking> viewAllBookings() throws BookingException{
		return this.bookingService.viewAllBookings();
	}
	
	@DeleteMapping("/deleteBooking/{bookingId}")
	public String deleteBooking(@PathVariable long bookingId) throws BookingException{
		return this.bookingService.deleteBooking(bookingId);
	}
	
	@GetMapping("/validateBooking/{bookingId}")
	public Booking validateBooking(@PathVariable long bookingId) throws BookingException{
		return this.bookingService.validateBooking(bookingId);
	}
	
	@GetMapping("/validatePassenger/{passengerId}")
	public Passenger validatePassenger(@PathVariable long passengerId) throws PassengerException{
		return this.bookingService.validatePassenger(passengerId);
	}

}
