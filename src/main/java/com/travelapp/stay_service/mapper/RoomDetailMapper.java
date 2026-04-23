package com.travelapp.stay_service.mapper;

import com.travelapp.stay_service.entities.RoomDetail;
import com.travelapp.stay_service.model.RoomDetailDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, componentModel = "spring")
public interface RoomDetailMapper {
    RoomDetailDto roomDetailToRoomDetailDto(RoomDetail roomDetail);

    RoomDetail roomDetailDtoToRoomDetail(RoomDetailDto roomDetailDto);
}
