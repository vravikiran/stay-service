package com.travelapp.stay_service.entities;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class StayRoomMappingDto {
    private String stayId;
    private Set<RoomCount> roomIdWithCount;

    public String getStayId() {
        return stayId;
    }

    public void setStayId(String stayId) {
        this.stayId = stayId;
    }

    public Set<RoomCount> getRoomIdWithCount() {
        return roomIdWithCount;
    }

    public void setRoomIdWithCount(Set<RoomCount> roomIdWithCount) {
        this.roomIdWithCount = roomIdWithCount;
    }

    public StayRoomMappingDto(String stayId, Set<RoomCount> roomIdWithCount) {
        this.stayId = stayId;
        this.roomIdWithCount = roomIdWithCount;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof StayRoomMappingDto that)) return false;
        return Objects.equals(stayId, that.stayId) && Objects.equals(roomIdWithCount, that.roomIdWithCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(stayId, roomIdWithCount);
    }
}
