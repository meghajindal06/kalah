package com.meghajindal.assignment.kalah.exceptionhandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Invalid request")
public class InputValidationException extends RuntimeException {

	
	
	
}
