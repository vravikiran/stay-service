package com.travelapp.stay_service.util;

import java.util.Set;

public class Constants {
	public static final String CITY = "address.city";
	public static final String LOCATION = "address.location";
	public static final String SUBLOCATION = "address.subLocation";
	public static final String PROPERTYTYPE = "propertyType";
	public static final String PROPERTYRATING = "propertyRating";
	public static final String USERRATING = "userRating";
	public static final Set<String> STAY_IMMUTABLE_FIELDS = Set.of("address", "restaurants","propertyType","rooms","hasRestaurant");
	public static final String STAYID = "_id";
	public static final String REST_RESTID = "restaurants.restId";
	public static final String RESTID ="restId";
	public static final String ROOMID = "rooms.roomId";
	public static final String RESTAURANTS = "restaurants";
	public static final String UPDATED_DATE = "updatedDate";
	public static final String RESTS_UPDT_DATE = "restaurants.$.updatedDate";
	public static final String CUISINES ="cuisines";
	public static final String ENDTIME ="endTime";
	public static final String STARTTIME="startTime";
	public static final String REST_CUISINES="restaurants.$.cuisines";
	public static final String REST_SUB_DOC ="restaurants.$.";
	public static final String ROOM_FACILITIES = "rooms.$.roomFacilities";
	public static final String REST_ENDTIME ="restaurants.$.endTime";
	public static final String REST_START_TIME = "restaurants.$.startTime";
	public static final String HAS_RESTAURANT = "hasRestaurant";
	public static final String STAY_FACILITIES ="facilities";
	public static final String STAY_RULES ="rules";
	public static final String ROOM_PRICE ="rooms.$.price";
}
