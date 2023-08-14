package com.fms.booking.repository;

import java.math.BigInteger;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fms.booking.entity.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long>{
	
	List<Booking> findBookingsByuserId(long userId);

	List<Booking> findBookingsBysheduleId(long sheduleId);
}
