package com.thenocturn.pos.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleNotFound(ResourceNotFoundException ex){
		
		Map<String,Object> response = new HashMap<>();
		response.put("message",ex.getMessage());
		response.put("status", 404);
		
		
		return new ResponseEntity<>(response,HttpStatus.NOT_FOUND);
		
	}
	
	  @ExceptionHandler(AccessDeniedException.class)
	    public ResponseEntity<?> handleAccessDenied() {
	        return ResponseEntity.status(HttpStatus.FORBIDDEN)
	                .body(Map.of("error", "Access Denied"));
	    }
	  
	  
	  @ExceptionHandler(Exception.class)
	    public ResponseEntity<?> handleGeneric(Exception ex) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body(Map.of("error", ex.getMessage()));
	    }


}
