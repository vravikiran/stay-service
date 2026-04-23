package com.travelapp.stay_service.model;

import lombok.Data;

import java.util.Set;

@Data
public class StayDetailDto {
    private Long id;
    private Set<RoomDetailDto> rooms;
    private boolean active;
    private boolean approved;
}
