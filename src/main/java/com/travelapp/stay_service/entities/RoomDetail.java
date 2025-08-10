package com.travelapp.stay_service.entities;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mongodb.lang.NonNull;
import com.travelapp.stay_service.enums.RoomCategoryEnum;
import com.travelapp.stay_service.enums.RoomViewEnum;
import com.travelapp.stay_service.util.EnumValidator;
import lombok.Data;

@Data
public class RoomDetail {
    private int roomId;
    private String name;
    @EnumValidator(enumClazz = RoomViewEnum.class, message = "Invalid oom view")
    private String roomView;
    private double size;
    private double price;
    private String roomImdUri;
    private Set<String> roomFacilities;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @EnumValidator(enumClazz = RoomCategoryEnum.class, message = "Invalid room category")
    private String roomCategory;
    @NonNull
    private Integer numberOfRooms = 1;
}
