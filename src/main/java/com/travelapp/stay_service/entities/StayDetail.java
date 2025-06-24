package com.travelapp.stay_service.entities;

import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.travelapp.stay_service.enums.PropertyRatingEnum;
import com.travelapp.stay_service.enums.PropertyTypeEnum;
import com.travelapp.stay_service.enums.UserRatingEnum;
@Document(collection = "StayDetail")
public class StayDetail {
	@Id
	private String id;
	private String name;
	private Set<RoomDetail> rooms;
	private boolean hasRestaurant = false;
	private boolean hasGym = false;
	private boolean hasSwimPool = false;
	private Address address;
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String stayImagesUri;
	private PropertyTypeEnum propertyType;
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private PropertyRatingEnum propertyRating;
	private UserRatingEnum userRating;
	private Set<Restaurant> restaurants;
	private boolean isactive = true;
	private boolean isApproved = true;
	private Set<String> facilities;
	private Set<String> rules;
	@JsonIgnore
	private LocalDate createdDate;
	@JsonIgnore
	private LocalDate updatedDate;

	public Set<String> getRules() {
		return rules;
	}

	public void setRules(Set<String> rules) {
		this.rules = rules;
	}

	public Set<String> getFacilities() {
		return facilities;
	}

	public void setFacilities(Set<String> facilities) {
		this.facilities = facilities;
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

	public PropertyRatingEnum getPropertyRating() {
		return propertyRating;
	}

	public void setPropertyRating(PropertyRatingEnum propertyRating) {
		this.propertyRating = propertyRating;
	}

	public boolean isApproved() {
		return isApproved;
	}

	public void setApproved(boolean isApproved) {
		this.isApproved = isApproved;
	}

	public boolean isIsactive() {
		return isactive;
	}

	public void setIsactive(boolean isactive) {
		this.isactive = isactive;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<RoomDetail> getRooms() {
		return rooms;
	}

	public void setRooms(Set<RoomDetail> rooms) {
		this.rooms = rooms;
	}

	public boolean isHasRestaurant() {
		return hasRestaurant;
	}

	public void setHasRestaurant(boolean hasRestaurant) {
		this.hasRestaurant = hasRestaurant;
	}

	public boolean isHasGym() {
		return hasGym;
	}

	public void setHasGym(boolean hasGym) {
		this.hasGym = hasGym;
	}

	public boolean isHasSwimPool() {
		return hasSwimPool;
	}

	public void setHasSwimPool(boolean hasSwimPool) {
		this.hasSwimPool = hasSwimPool;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getStayImagesUri() {
		return stayImagesUri;
	}

	public void setStayImagesUri(String stayImagesUri) {
		this.stayImagesUri = stayImagesUri;
	}

	public PropertyTypeEnum getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(PropertyTypeEnum propertyType) {
		this.propertyType = propertyType;
	}

	public UserRatingEnum getUserRating() {
		return userRating;
	}

	public void setUserRating(UserRatingEnum userRating) {
		this.userRating = userRating;
	}

	public Set<Restaurant> getRestaurants() {
		return restaurants;
	}

	public void setRestaurants(Set<Restaurant> restaurants) {
		this.restaurants = restaurants;
	}

	@Override
	public int hashCode() {
		return Objects.hash(address, id, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StayDetail other = (StayDetail) obj;
		return Objects.equals(address, other.address) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name);
	}
}
