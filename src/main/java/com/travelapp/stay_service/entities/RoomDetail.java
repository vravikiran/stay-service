package com.travelapp.stay_service.entities;

import java.util.Objects;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.mongodb.lang.NonNull;
import com.travelapp.stay_service.enums.RoomCategoryEnum;
import com.travelapp.stay_service.enums.RoomViewEnum;

public class RoomDetail {
	private int roomId;
	private String name;
	private RoomViewEnum roomView;
	private double size;
	private double price;
	private String roomImdUri;
	private Set<String> roomFacilities;
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private RoomCategoryEnum roomCategory;
    @NonNull
    private Integer numberOfRooms = 1 ;

    public RoomDetail() {
    }

    public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RoomViewEnum getRoomView() {
		return roomView;
	}

	public void setRoomView(RoomViewEnum roomView) {
		this.roomView = roomView;
	}

	public double getSize() {
		return size;
	}

	public void setSize(double size) {
		this.size = size;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getRoomImdUri() {
		return roomImdUri;
	}

	public void setRoomImdUri(String roomImdUri) {
		this.roomImdUri = roomImdUri;
	}

	public Set<String> getRoomFacilities() {
		return roomFacilities;
	}

	public void setRoomFacilities(Set<String> roomFacilities) {
		this.roomFacilities = roomFacilities;
	}

	public RoomCategoryEnum getRoomCategory() {
		return roomCategory;
	}

	public void setRoomCategory(RoomCategoryEnum roomCategory) {
		this.roomCategory = roomCategory;
	}

    @NonNull
    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(@NonNull Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
    }

    @Override
	public int hashCode() {
		return Objects.hash(name, roomId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RoomDetail other = (RoomDetail) obj;
		return Objects.equals(name, other.name) && roomId == other.roomId;
	}

}
