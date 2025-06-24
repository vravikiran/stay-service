package com.travelapp.stay_service.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.travelapp.stay_service.enums.CuisineTypeEnum;
import com.travelapp.stay_service.enums.RestaurantTypeEnum;

public class Restaurant {
	private int restId;
	private String name;
	private List<CuisineTypeEnum> cuisines;
	private RestaurantTypeEnum restaurantType;
	@JsonIgnore
	private LocalDate createdDate;
	@JsonIgnore
	private LocalDate updatedDate;
	private LocalTime startTime;
	private LocalTime endTime;

	public LocalTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalTime startTime) {
		this.startTime = startTime;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalTime endTime) {
		this.endTime = endTime;
	}

	public LocalDate getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(LocalDate createdDate) {
		this.createdDate = createdDate;
	}

	public LocalDate getUpdatedDate() {
		return updatedDate;
	}

	public void setUpdatedDate(LocalDate updatedDate) {
		this.updatedDate = updatedDate;
	}

	public int getRestId() {
		return restId;
	}

	public void setRestId(int restId) {
		this.restId = restId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<CuisineTypeEnum> getCuisines() {
		return cuisines;
	}

	public void setCuisines(List<CuisineTypeEnum> cuisines) {
		this.cuisines = cuisines;
	}

	public RestaurantTypeEnum getRestaurantType() {
		return restaurantType;
	}

	public void setRestaurantType(RestaurantTypeEnum restaurantType) {
		this.restaurantType = restaurantType;
	}

	@Override
	public int hashCode() {
		return Objects.hash(restId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Restaurant other = (Restaurant) obj;
		return restId == other.restId;
	}

	public Restaurant() {
		super();
	}
}
