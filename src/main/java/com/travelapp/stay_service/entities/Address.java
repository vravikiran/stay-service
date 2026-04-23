package com.travelapp.stay_service.entities;

import lombok.Data;

@Data
public class Address {
	private String location;
	private String subLocation;
	private String city;
	private String state;
	private String country;
	private int zipCode;
	private String addressLine1;
	private String addressLine2;
}
