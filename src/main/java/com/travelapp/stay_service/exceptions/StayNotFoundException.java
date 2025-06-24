package com.travelapp.stay_service.exceptions;

public class StayNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public StayNotFoundException() {
		super();
	}

	public StayNotFoundException(String message) {
		super(message);
	}

}
