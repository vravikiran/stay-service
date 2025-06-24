package com.travelapp.stay_service.exceptions;

public class RestaurantNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public RestaurantNotFoundException() {
		super();
	}

	public RestaurantNotFoundException(String message) {
		super(message);
	}

}
