package com.travelapp.stay_service.exceptions;

public class DuplicateRestaurantException extends Exception {

	private static final long serialVersionUID = 1L;

	public DuplicateRestaurantException() {
		super();
	}

	public DuplicateRestaurantException(String message) {
		super(message);
	}

}
