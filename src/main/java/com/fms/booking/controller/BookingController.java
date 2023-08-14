package com.fms.booking.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fms.booking.DTO.BookingDTO;
import com.fms.booking.entity.Booking;
import com.fms.booking.entity.Passenger;
import com.fms.booking.exception.BookingException;
import com.fms.booking.exception.PassengerException;
import com.fms.booking.service.BookingService;

@RestController
@EnableWebMvc
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/booking")
public class BookingController {

	private static final Logger logger = LoggerFactory.getLogger(BookingController.class);

	@Autowired

	private BookingService bookingService;

	@PostMapping(value = "/addBooking")

	public ResponseEntity<Booking> addBooking(@Valid @RequestBody Booking booking)
			throws BookingException, PassengerException {

		logger.info("Received request to book {}", booking);

		return ResponseEntity.status(HttpStatus.OK).body(this.bookingService.addBooking(booking));

	}

	@PutMapping("/modifyBooking/modifyPassenger/booking")

	public ResponseEntity<Booking> modifyBooking(@Valid @RequestBody Booking booking) throws BookingException {

		logger.info("Received request to modify booking details to:{}", booking);

		return ResponseEntity.status(HttpStatus.OK).body(this.bookingService.modifyBooking(booking));

	}

	@PutMapping("/cancelBooking/{bookingId}")

	public ResponseEntity<Booking> cancelBooking(@PathVariable long bookingId) throws BookingException {

		logger.info("Received request to cancel booking with id:{}", bookingId);

		return ResponseEntity.status(HttpStatus.OK).body(this.bookingService.cancelBooking(bookingId));

	}

	@PutMapping("/cancelSingleTicket/booking/{passengerId}")

	public ResponseEntity<Booking> cancelTicketFromBooking(@Valid @RequestBody Booking booking,
			@PathVariable long passengerId, long price) throws BookingException, PassengerException {

		logger.info("Received request to cancel passenger ticket with id {} from booking :{}", passengerId,
				booking.getBookingId());

		return ResponseEntity.status(HttpStatus.OK)
				.body(this.bookingService.cancelTicketFromBooking(booking, passengerId, price));

	}

	@GetMapping("/userBookings/{userId}")

	public ResponseEntity<List<Booking>> viewAllBookingsOfUser(@Valid @PathVariable long userId)
			throws BookingException {

		logger.info("Received request to view bookings of userId: {}", userId);

		return ResponseEntity.status(HttpStatus.OK).body(this.bookingService.viewAllBookingsOfUser(userId));

	}

	@GetMapping("/allBookings")

	public ResponseEntity<List<Booking>> viewAllBookings() throws BookingException {

		logger.info("Received request to view all existed bookings");

		return ResponseEntity.status(HttpStatus.OK).body(this.bookingService.viewAllBookings());

	}

	@DeleteMapping("/deleteBooking/{bookingId}")

	public ResponseEntity<String> deleteBooking(@PathVariable long bookingId) throws BookingException {

		logger.info("Received request to delet booking with id: {}", bookingId);

		return ResponseEntity.status(HttpStatus.OK).body(this.bookingService.deleteBooking(bookingId));

	}
	
	@GetMapping("/passengerCount/{sheduleId}")

	public long bookedTicketsCount(@Valid @PathVariable long sheduleId) throws BookingException{

	logger.info("Received request to view bookings of sheduleId: {}", sheduleId);

	return this.bookingService.bookedTicketsCount(sheduleId);

	}
	
	@GetMapping("/getBooking/{bookingId}")

	public BookingDTO getBooking(@PathVariable Long bookingId)throws BookingException{

	return this.bookingService.getBookingById(bookingId);

	}

	@GetMapping("/validateBooking/{bookingId}")

	 public Booking validateBooking(@PathVariable long bookingId) throws
	 BookingException{

//	logger.info("Received request to verify booking with
//			bookingId:{}",bookingId);

	 return this.bookingService.validateBooking(bookingId);

	}
	
	

	

	// @GetMapping("/validatePassenger/{passengerId}")

	// public Passenger validatePassenger(@PathVariable long passengerId) throws
	// PassengerException{

	// logger.info("Received request to verify passenger with passenger
	// Id:{}",passengerId);

	//

	// return this.bookingService.validatePassenger(passengerId);

	// }

}
