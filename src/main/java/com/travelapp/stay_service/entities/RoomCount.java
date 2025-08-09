package com.travelapp.stay_service.entities;

import java.util.Objects;

public class RoomCount {
    private int roomId;
    private int noOfRooms;

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getNoOfRooms() {
        return noOfRooms;
    }

    public void setNoOfRooms(int noOfRooms) {
        this.noOfRooms = noOfRooms;
    }

    public RoomCount(int roomId, int noOfRooms) {
        this.roomId = roomId;
        this.noOfRooms = noOfRooms;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof RoomCount roomCount)) return false;
        return roomId == roomCount.roomId && noOfRooms == roomCount.noOfRooms;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomId, noOfRooms);
    }
}
