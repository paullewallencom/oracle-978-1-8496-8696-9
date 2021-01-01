package com.packt.theater.services;

/**
 * Generic exception for reservation errors
 * @author markito
 *
 */
public class ReservationException extends Exception {

	private static final long serialVersionUID = -4179783709556680596L;
	
	public ReservationException() {
		super("Generic reservation exception");
	}
	
	public ReservationException(String message) {
		super(message);
	}
	
}
