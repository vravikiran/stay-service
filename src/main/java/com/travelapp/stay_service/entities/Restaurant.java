package com.travelapp.stay_service.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.travelapp.stay_service.enums.CuisineTypeEnum;
import com.travelapp.stay_service.enums.RestaurantTypeEnum;
import com.travelapp.stay_service.util.EnumValidator;
import lombok.Data;

@Data
public class Restaurant {
	private int restId;
	private String name;
    @EnumValidator(enumClazz = CuisineTypeEnum.class,message = "one or more cuisine types are invalid")
	private List< String> cuisines;
    @EnumValidator(enumClazz = RestaurantTypeEnum.class, message = "restaurant type is invalid")
	private String restaurantType;
	@JsonIgnore
	private LocalDate createdDate;
	@JsonIgnore
	private LocalDate updatedDate;
	private LocalTime startTime;
	private LocalTime endTime;
}
