package com.fms.booking.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fms.booking.exception.BookingException;
import com.fms.booking.exception.PassengerException;

@RestControllerAdvice
public class BookingControllerAdvice {
	
	@ExceptionHandler(BookingException.class)
    public ResponseEntity<String> BookingException(BookingException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
	
	@ExceptionHandler(PassengerException.class)
	@ResponseStatus(value = HttpStatus.NOT_FOUND)
	public String handlePassengerException(Exception e) {
		return e.getMessage();
	}
//	@ExceptionHandler(RuntimeException.class)
//	public String InternalServerErrorException(Exception e) {
//		return "Internal server error";
//	}
//	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
		Map<String, String> errors = new HashMap<>();

		ex.getBindingResult().getAllErrors().forEach(error -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errors.put(fieldName, errorMessage);
		});
		return errors;
	}

	

}
