package com.travelapp.stay_service.mapper;

import com.travelapp.stay_service.entities.StayDetail;
import com.travelapp.stay_service.model.StayDetailDto;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE, uses = {RoomDetailMapper.class}, componentModel = "spring")
public interface StayDetailMapper {
    StayDetailDto stayDetailToStayDetailDto(StayDetail stayDetail);

    StayDetail stayDetailDtoToStayDetail(StayDetailDto stayDetailDto);
}
