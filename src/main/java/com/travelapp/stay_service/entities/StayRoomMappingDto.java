package com.travelapp.stay_service.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class StayRoomMappingDto {
    private long stayId;
    private Set<RoomCount> roomIdWithCount;
}
