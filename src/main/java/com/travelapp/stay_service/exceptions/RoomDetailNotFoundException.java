package com.travelapp.stay_service.exceptions;

public class RoomDetailNotFoundException extends Exception {

	private static final long serialVersionUID = 1L;

	public RoomDetailNotFoundException() {
		super();
	}

	public RoomDetailNotFoundException(String message) {
		super(message);
	}

}
