package com.fms.booking.Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.fms.booking.entity.Booking;
import com.fms.booking.entity.Booking.BookingStatus;
import com.fms.booking.entity.Passenger;
import com.fms.booking.exception.BookingException;
import com.fms.booking.exception.PassengerException;
import com.fms.booking.repository.BookingRepository;
import com.fms.booking.repository.PassengerRepository;
import com.fms.booking.service.BookingService;
import com.fms.booking.service.BookingServiceImpl;

@SpringBootTest
class FmsBookingTests {
	

	@Mock
	private BookingRepository bookingRepo;
	
	@Mock
	private BookingService bookingService;
	
	
	@Autowired
	private BookingService iBookingService;
	
	@MockBean
	private PassengerRepository passengerRepo;
	
	@MockBean
	private BookingRepository repository;
	
	@InjectMocks
	BookingService bookingServiceMock = new BookingServiceImpl();

	public static Passenger demo1() {
		Passenger s = new Passenger();
		s.setPassengerName("Akash");
		s.setPassengerAge(23);
		s.setPassengerId(1);
		s.setGender("Female");
		s.setLugguage(5);
		s.setUid("143143143143");
		s.setContactNo("7036011143");
		return s;
	}
	
	
	public static Booking demo2() {
	    Booking booking = new Booking();
	    List<Passenger> passengers = new ArrayList<>();
	    passengers.add(new Passenger(1, "John Doe", "9876543210", "ABC123XYZ", 30, "Male", 10.0));
	    booking.setBookingId(1);
	    booking.setUserId(1);
	    booking.setPassengerList(passengers);
	    booking.setPassengerCount(passengers.size());
	    booking.setBookingDate(LocalDate.now());
	    booking.setTicketCost(3000.0);
	    booking.setBookingStatus(BookingStatus.BOOKED);
		return booking;
	}
	
	public static Booking demo3() {
		Booking booking2 = new Booking();
	    List<Passenger> passengers = new ArrayList<>();
	    passengers.add(new Passenger(1, "Akash", "9876543210", "ABC123XYZ", 30, "Male", 10.0));
	    booking2.setBookingId(1);
	    booking2.setPassengerList(passengers);
	    booking2.setPassengerCount(passengers.size());
	    booking2.setBookingDate(LocalDate.now());
	    booking2.setTicketCost(3000.0);
	    booking2.setBookingStatus(BookingStatus.BOOKED);
	    return  booking2;
	
	}
	
	Booking booking1 = FmsBookingTests.demo2();
	Booking booking3=FmsBookingTests.demo3();
	Passenger passenger1= FmsBookingTests.demo1();
	
	
	@Test
	void validBookingAdditionTest() throws BookingException {
		Mockito.when(bookingRepo.findById(booking1.getBookingId())).thenReturn(Optional.empty());
		Assertions.assertNotNull(booking1);
	}
	
	@Test
	void validAddBookingTest2() throws Exception{
		
		Mockito.when(repository.save(booking1)).thenReturn(booking1);
		assertEquals(booking1, iBookingService.addBooking(booking1));
	}
	
	@Test
	void invalidBookingAdditionTest() throws BookingException {
		Mockito.when(bookingRepo.findById(booking1.getBookingId())).thenReturn(Optional.of(booking1));
		BookingException ex = Assertions.assertThrows(BookingException.class,
				() -> bookingServiceMock.addBooking(booking1));
		Assertions.assertEquals("Booking already present", ex.getMessage());
	}
	
	@Test
	void validValidateBookingTest() throws  BookingException {
		Mockito.when(bookingRepo.findById(booking1.getBookingId())).thenReturn(Optional.of(booking1));
		Assertions.assertEquals(bookingServiceMock.validateBooking(booking1.getBookingId()), booking1);
	}
	
	@Test
	void invalidValidateBookingTest() throws BookingException {
		Mockito.when(bookingRepo.findById(booking3.getBookingId())).thenReturn(Optional.empty());
		BookingException ex = Assertions.assertThrows(BookingException.class,
				() -> bookingServiceMock.validateBooking(booking3.getBookingId()));
		Assertions.assertEquals("No booking found with id " + booking3.getBookingId() , ex.getMessage());
	}
	
	
	@Test
	void validValidatePassengerTest() throws PassengerException {
		Passenger passenger=new Passenger(1, "Akash", "9876543210", "ABC123XYZ", 30, "Male", 10.0);
		when(passengerRepo.findById((long)1)).thenReturn(Optional.of(passenger));
		when(passengerRepo.save(passenger)).thenReturn(passenger);
		Mockito.when(repository.save(booking1)).thenReturn(booking1);
		when(repository.findById(booking1.getBookingId())).thenReturn(Optional.of(booking1));
	
			assertEquals(passenger, iBookingService.validatePassenger(1));
		
	}
	
	@Test
	void InvalidValidatePassengerTest() throws PassengerException {
	    Passenger passenger= new Passenger(3, "John Doe", "9876543210", "ABC123XYZ", 30, "Male", 10.0);

		when(repository.findById(booking3.getBookingId())).thenReturn(Optional.of(booking3));
		when(passengerRepo.findById(passenger.getPassengerId())).thenReturn(Optional.empty());
		PassengerException ex1 = Assertions.assertThrows(PassengerException.class,
				() -> iBookingService.validatePassenger(3));
	
		Mockito.when(repository.save(booking1)).thenReturn(booking1);
		
			assertEquals("No passenger booked with this id", ex1.getMessage());
		
	}
	

	@Test 
	void validViewAllBookingsOfUserTest() throws BookingException {
		List<Booking> existedBooking= new ArrayList<>();
		existedBooking.add(booking1);
		Mockito.when(bookingRepo.findBookingsByuserId(booking1.getUserId())).thenReturn
		(existedBooking);
		Assertions.assertEquals(bookingServiceMock.viewAllBookingsOfUser(1),existedBooking);
	}
	
	@Test
	void invalidViewAllBookingsOfUserTest() throws BookingException {
		List<Booking> existedBooking= new ArrayList<>();
		Mockito.when(bookingRepo.findBookingsByuserId(booking1.getUserId())).thenReturn(existedBooking);
		BookingException ex = Assertions.assertThrows(BookingException.class,
				() -> bookingServiceMock.viewAllBookingsOfUser(booking1.getUserId()));
		Assertions.assertEquals("No Booking done by this id " ,ex.getMessage());
	}
	
	@Test 
	void validViewAllBookingsTest() throws BookingException {
		List<Booking> existedBooking= new ArrayList<>();
		existedBooking.add(booking1);
		Mockito.when(bookingRepo.findAll()).thenReturn(existedBooking);
		Assertions.assertEquals(bookingServiceMock.viewAllBookings(),existedBooking);
	}
	
	@Test
	void invalidViewAllBookingsTest() throws BookingException {
		Mockito.when(bookingRepo.findById(booking3.getBookingId())).thenReturn(Optional.empty());
		BookingException ex = Assertions.assertThrows(BookingException.class,
				() -> bookingServiceMock.viewAllBookings());
		Assertions.assertEquals("No bookings found" , ex.getMessage());
	}
	
	
	
	@Test
	void validDeleteBookingTest() throws BookingException {
		Mockito.when(bookingRepo.findById(booking1.getBookingId())).thenReturn(Optional.of(booking1));
		Assertions.assertEquals(bookingServiceMock.deleteBooking(booking1.getBookingId()),"Booking with id "+booking1.getBookingId()+" deleted successfully");
	}
	
	@Test
	void invalidDeleteBookingTest() throws BookingException, PassengerException {
		when(repository.findById(booking3.getBookingId())).thenReturn(Optional.empty());
		BookingException ex = Assertions.assertThrows(BookingException.class,
				() -> iBookingService.deleteBooking(booking3.getBookingId()));
		assertEquals("No booking found with id "+ booking3.getBookingId(),ex.getMessage());
	}
	

	@Test
	void modifyBookingTest() throws BookingException {
		when(repository.findById(booking3.getBookingId())).thenReturn(Optional.of(booking3));
		when(repository.save(booking3)).thenReturn(booking3);
		assertEquals(booking3, iBookingService.modifyBooking(booking3));
	}
	
	@Test
	void invalidModifyBookingTest() throws BookingException, PassengerException {
		when(repository.findById(booking3.getBookingId())).thenReturn(Optional.empty());
		BookingException ex = Assertions.assertThrows(BookingException.class,
				() -> iBookingService.modifyBooking(booking3));
		assertEquals("Could not update!! No booking with id " + booking3.getBookingId() + " found",ex.getMessage());
	}

	@Test
	void validCancelBooking() throws BookingException, PassengerException {
		when(repository.findById(booking3.getBookingId())).thenReturn(Optional.of(booking3));
		when(repository.save(booking3)).thenReturn(booking3);
		assertEquals(BookingStatus.CANCELLED, iBookingService.cancelBooking(booking3.getBookingId()).getBookingStatus());
	}
	
	@Test
	void invalidCancelBooking() throws BookingException, PassengerException {
		when(repository.findById(booking3.getBookingId())).thenReturn(Optional.empty());
		BookingException ex = Assertions.assertThrows(BookingException.class,
				() -> iBookingService.cancelBooking(booking3.getBookingId()));
		assertEquals("Could not cancel!! No booking with id " +booking3.getBookingId() + " found",ex.getMessage());
	}
	
	
	
	//dummy
	@Test
	void cancelTicketFromBooking() throws BookingException, PassengerException {
		Booking booking4=new Booking();
		List<Passenger> passengers1 = new ArrayList<>();
		LocalDate date1=LocalDate.now();
	    Passenger passngr= new Passenger(3, "John Doe", "9876543210", "ABC123XYZ", 30, "Male", 10.0);
	    passengers1.add(passngr);
	    Booking bkng=new Booking((long)8,(long)9,1,date1,3000.0,BookingStatus.BOOKED,passengers1);

		
		
		Booking booking2 = new Booking();
	    List<Passenger> passengers = new ArrayList<>();
	    passengers.add(new Passenger(1, "Akash", "9876543210", "ABC123XYZ", 30, "Male", 10.0));
	    passengers.add(new Passenger(3, "John Doe", "9876543210", "ABC123XYZ", 30, "Male", 10.0));
	    Passenger passenger= new Passenger(3, "John Doe", "9876543210", "ABC123XYZ", 30, "Male", 10.0);
	    booking2.setBookingId(1);
	    booking2.setPassengerList(passengers);
	    booking2.setPassengerCount(passengers.size());
	    booking2.setBookingDate(LocalDate.now());
	    booking2.setTicketCost(3000.0);
	    booking2.setBookingStatus(BookingStatus.BOOKED);
	    
		
		when(repository.findById(booking2.getBookingId())).thenReturn(Optional.of(booking2));
		when(passengerRepo.findById((long)3)).thenReturn(Optional.of(passenger));
		when(repository.save(booking2)).thenReturn(booking2);
		when(passengerRepo.save(passenger)).thenReturn(passenger);
		assertEquals(booking3.getBookingDate(), iBookingService.cancelTicketFromBooking(booking2,(long)3,(double)10).getBookingDate());
	}
	
	@Test
	void invalidCancelTicketFromBooking() throws BookingException, PassengerException {
		Booking booking2 = new Booking();
	    List<Passenger> passengers = new ArrayList<>();
	    passengers.add(new Passenger(1, "Akash", "9876543210", "ABC123XYZ", 30, "Male", 10.0));
	    Passenger passenger= new Passenger(3, "John Doe", "9876543210", "ABC123XYZ", 30, "Male", 10.0);
	    booking2.setBookingId(1);
	    booking2.setPassengerList(passengers);
	    booking2.setPassengerCount(passengers.size());
	    booking2.setBookingDate(LocalDate.now());
	    booking2.setTicketCost(3000.0);
	    booking2.setBookingStatus(BookingStatus.BOOKED);
		when(repository.findById(booking2.getBookingId())).thenReturn(Optional.empty());
		when(passengerRepo.findById(passenger.getPassengerId())).thenReturn(Optional.empty());
		BookingException ex = Assertions.assertThrows(BookingException.class,
				() -> iBookingService.cancelTicketFromBooking(booking2,4,1000));
		assertEquals("Booking not found",ex.getMessage());
		
		when(repository.findById(booking3.getBookingId())).thenReturn(Optional.of(booking3));
		when(passengerRepo.findById(passenger.getPassengerId())).thenReturn(Optional.empty());
		PassengerException ex1 = Assertions.assertThrows(PassengerException.class,
				() -> iBookingService.cancelTicketFromBooking(booking3,4,1000));
		assertEquals("Passenger not found",ex1.getMessage());
		
		
		
	}
}
	


