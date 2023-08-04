package com.fms.booking.Tests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.fms.booking.entity.Booking;
import com.fms.booking.entity.Passenger;
import com.fms.booking.repository.BookingRepository;
import com.fms.booking.repository.PassengerRepository;
import com.fms.booking.service.BookingService;

@SpringBootTest
class FmsBookingTests {

	@Autowired
	private BookingRepository bookingRepo;
	
	@Autowired 
	private BookingService bookingService;
	
	@Autowired
	private PassengerRepository passengerRepo;

	@Test
	void contextLoads() {
	}

	
	

}
