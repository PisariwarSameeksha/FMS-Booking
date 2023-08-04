package com.fms.booking.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fms.booking.entity.Passenger;


@Repository
public interface PassengerRepository  extends JpaRepository<Passenger, Long>{

}
