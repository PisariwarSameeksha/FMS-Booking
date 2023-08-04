package com.fms.booking.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fms.booking.entity.Booking;
import com.fms.booking.entity.Booking.BookingStatus;
import com.fms.booking.entity.Passenger;
import com.fms.booking.exception.BookingException;
import com.fms.booking.exception.PassengerException;
import com.fms.booking.repository.BookingRepository;
import com.fms.booking.repository.PassengerRepository;

@Service
public class BookingServiceImpl implements BookingService{
	
	@Autowired
	private BookingRepository bookingRepo;
	
	@Autowired
	private PassengerRepository passengerRepo;

	@Override
	public Booking addBooking(Booking booking) {
		
		   List<Passenger> passengers = booking.getPassengerList();
	        for (Passenger passenger : passengers) {
	        	passengerRepo.save(passenger);
	        }
	        booking.setPassengerCount(passengers.size());
//	        booking.setTicketCost(booking.getPassengerCount()*price);
	        return bookingRepo.save(booking);
	    }


	@Override
	public Booking modifyBooking(Booking booking) throws BookingException {
		Optional<Booking> existingBooking = bookingRepo.findById(booking.getBookingId());
		if (!existingBooking.isPresent()) {
			throw new BookingException("Could not update!! No booking with id " + booking.getBookingId() + " found");
		}
		
		Booking bookingToBeUpadated= existingBooking.get();
		List<Passenger> passengers = booking.getPassengerList();
        for (Passenger passenger : passengers) {
        	passengerRepo.save(passenger);
        }
		
		
		bookingToBeUpadated.setBookingDate(booking.getBookingDate());
		bookingToBeUpadated.setBookingStatus(booking.getBookingStatus());
		bookingToBeUpadated.setPassengerCount(booking.getPassengerCount());
		bookingToBeUpadated.setPassengerList(booking.getPassengerList());
		bookingToBeUpadated.setTicketCost(booking.getTicketCost());
		return bookingRepo.save(bookingToBeUpadated);
	}
	
	
	@Override
	public Booking cancelBooking(long bookingId) throws BookingException {
		Optional<Booking> existingBooking = bookingRepo.findById(bookingId);
		if (!existingBooking.isPresent()) {
			throw new BookingException("Could not cancel!! No booking with id " + bookingId + " found");
		}
		Booking bookingToBeCancelled= existingBooking.get();
		bookingToBeCancelled.setBookingStatus(BookingStatus.CANCELLED);
		bookingToBeCancelled.setTicketCost(0.0);
		return bookingRepo.save(bookingToBeCancelled);		
	} 
	
	
	@Override
	public Booking validateBooking(long bookingId) throws BookingException {
		Optional<Booking> foundBooking = bookingRepo.findById(bookingId);
		if (!foundBooking.isPresent()) {
			throw new BookingException("No booking found with id " + bookingId );
		}
		return foundBooking.get();
	}

	@Override
	public Passenger validatePassenger(long passengerId) throws PassengerException {
		Optional<Passenger> foundPasssenger = passengerRepo.findById(passengerId);
		if (!foundPasssenger.isPresent()) {
			throw new PassengerException("No passenger bookedwith id " + passengerId );
		}
		return foundPasssenger.get();
		
	}

	@Override
	public List<Booking> viewAllBookingsOfUser(BigInteger userId) throws BookingException {
		List<Booking> findBooking = bookingRepo.findBookingsByuserId(userId);
		if (!findBooking.isEmpty()) {
			return bookingRepo.findBookingsByuserId(userId);
		}
		throw new BookingException("No Booking done by id " + userId );
	}

	@Override
	public List<Booking> viewAllBookings() throws BookingException {
		Iterable<Booking> order2 = bookingRepo.findAll();
		List<Booking> bookingList = new ArrayList<>();
		order2.forEach(order ->{
			bookingList.add(order);
		});
		if (bookingList.isEmpty())
			throw new BookingException("No bookings found");
		return bookingList;
	}


	@Override
	public Booking cancelTicketFromBooking(Booking booking1, long passengerId,double price) throws PassengerException, BookingException {
		Optional<Booking> booking2=bookingRepo.findById(booking1.getBookingId());
		if(!booking2.isPresent()) {
			throw new BookingException("Booking not found");
		}
		Optional<Passenger> passenger = passengerRepo.findById(passengerId);
		if (!passenger.isPresent()) {
				throw new PassengerException("Passenger not found");	
		}
		Booking booking=booking2.get();
		Passenger passenger1 = passenger.get();
		if (booking.getPassengerList().contains(passenger1)) {
					Double amount1 = booking.getTicketCost()-price;
					booking.setTicketCost(amount1);
					booking.getPassengerList().remove(passenger1);
					bookingRepo.save(booking);
		}		
		return booking;
	}

	@Override
	public String deleteBooking(long bookingId) throws BookingException {
		Optional<Booking> bookingToBeDeleted = bookingRepo.findById(bookingId);
		if(bookingToBeDeleted.isPresent()) {
			this.bookingRepo.deleteById(bookingId);
			return "Booking with id "+bookingId+"deleted successfully";
		}
		else {
			throw new BookingException("No booking found with id " + bookingId);
		}
	}

}
