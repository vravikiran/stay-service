package com.travelapp.stay_service.repositories;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bson.Document;
import org.springframework.data.domain.Page;

import com.travelapp.stay_service.entities.StayDetail;
import com.travelapp.stay_service.exceptions.RestaurantNotFoundException;
import com.travelapp.stay_service.exceptions.RoomDetailNotFoundException;
import com.travelapp.stay_service.exceptions.StayNotFoundException;
import org.springframework.data.mongodb.core.aggregation.Aggregation;

public interface StayDetailCustomRepository {
	public Page<StayDetail> searchStays(String city, String location, String subLocation, String propertyType,
			String propertyRating, String userRating, int page, int size);

	public StayDetail updateRestaurantDetailsAtStay(Long stayId, int restId, Map<String, Object> updatedFields)
			throws RestaurantNotFoundException;

	public void removeRestuarantFromStay(Long stayId, int restId) throws RestaurantNotFoundException;

	public void updateRoomFacilities(Long stayId, int roomId, Set<String> roomFacilities)
			throws RoomDetailNotFoundException;

	public StayDetail updateStay(Long stayId, Map<String, Object> updatedFields) throws StayNotFoundException;

	public void updateRoomPrice(Long stayId, int roomId, double price) throws RoomDetailNotFoundException;

	public List<StayDetail> search(String  textToSearch);

}
