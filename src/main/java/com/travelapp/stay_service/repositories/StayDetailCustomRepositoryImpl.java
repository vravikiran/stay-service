package com.travelapp.stay_service.repositories;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.travelapp.stay_service.entities.RoomDetail;
import com.travelapp.stay_service.entities.StayDetail;
import com.travelapp.stay_service.enums.PropertyRatingEnum;
import com.travelapp.stay_service.enums.PropertyTypeEnum;
import com.travelapp.stay_service.enums.UserRatingEnum;
import com.travelapp.stay_service.exceptions.RestaurantNotFoundException;
import com.travelapp.stay_service.exceptions.RoomDetailNotFoundException;
import com.travelapp.stay_service.exceptions.StayNotFoundException;
import com.travelapp.stay_service.util.Constants;

public class StayDetailCustomRepositoryImpl implements StayDetailCustomRepository {
	@Autowired
	MongoTemplate mongoTemplate;
	//mongodb://admin:walnut16%40@localhost:27017/?authSource=admin

	@Override
	public Page<StayDetail> searchStays(String city, String location, String subLocation, String propertyType,
			String propertyRating, String userRating, int page, int size) {
		Pattern cityPattern = Pattern.compile("^" + Pattern.quote(city) + "$", Pattern.CASE_INSENSITIVE);
		Criteria criteria = Criteria.where(Constants.CITY).regex(cityPattern);
		if (location != null) {
			Pattern locPattern = Pattern.compile("^" + Pattern.quote(location) + "$", Pattern.CASE_INSENSITIVE);
			criteria.and(Constants.LOCATION).regex(locPattern);
		}
		if (subLocation != null) {
			Pattern subLocPattern = Pattern.compile("^" + Pattern.quote(subLocation) + "$", Pattern.CASE_INSENSITIVE);
			criteria.and(Constants.SUBLOCATION).regex(subLocPattern);
		}
		if (propertyType != null && PropertyTypeEnum.valueOf(propertyType.toUpperCase()) != null) {
			criteria.and(Constants.PROPERTYTYPE).is(propertyType.toUpperCase());
		}
		if (propertyRating != null && PropertyRatingEnum.valueOf(propertyRating.toUpperCase()) != null) {
			criteria.and(Constants.PROPERTYRATING).is(propertyRating.toUpperCase());
		}
		if (userRating != null && UserRatingEnum.valueOf(userRating.toUpperCase()) != null) {
			criteria.and(Constants.USERRATING).is(userRating.toUpperCase());
		}
		Query query = new Query(criteria);
		query.with(PageRequest.of(page, size));
		List<StayDetail> stays = mongoTemplate.find(query, StayDetail.class);
		Map<Object, Set<Object>> stayWithRooms =  stays.stream().collect(Collectors.groupingBy(a->a.getId(), Collectors.mapping(a->a.getRooms(), Collectors.toSet())));
		System.out.println(stayWithRooms.size());
		long count = mongoTemplate.count(Query.of(query).limit(-1).skip(-1), StayDetail.class);
		return new PageImpl<>(stays, PageRequest.of(page, size), count);
	}

	@Override
	public StayDetail updateRestaurantDetailsAtStay(String stayId, int restId, Map<String, Object> updatedFields)
			throws RestaurantNotFoundException {
		Query query = new Query(Criteria.where(Constants.STAYID).is(stayId).and(Constants.REST_RESTID).is(restId));
		if (mongoTemplate.exists(query, StayDetail.class)) {
			Update update = new Update().set(Constants.RESTS_UPDT_DATE, LocalDate.now());
			if (updatedFields.containsKey(Constants.CUISINES)) {
				update.addToSet(Constants.REST_CUISINES).each(updatedFields.get(Constants.CUISINES));
				updatedFields.remove(Constants.CUISINES);
			}
			if (updatedFields.containsKey(Constants.ENDTIME)) {
				update.set(Constants.REST_ENDTIME, LocalTime.parse((String) updatedFields.get(Constants.ENDTIME)));
				updatedFields.remove(Constants.ENDTIME);
			}
			if (updatedFields.containsKey(Constants.STARTTIME)) {
				update.set(Constants.REST_START_TIME, LocalTime.parse((String) updatedFields.get(Constants.STARTTIME)));
				updatedFields.remove(Constants.STARTTIME);
			}
			updatedFields.entrySet().stream()
					.forEach(entry -> update.set(Constants.REST_SUB_DOC + entry.getKey(), entry.getValue()));
			mongoTemplate.updateFirst(query, update, StayDetail.class);
			Query fetchQuery = new Query(Criteria.where(Constants.STAYID).is(stayId));
			return mongoTemplate.findOne(fetchQuery, StayDetail.class);
		} else {
			throw new RestaurantNotFoundException("Restaurant with given id doesn't exists in the stay :; " + restId);
		}
	}

	@Override
	public void removeRestuarantFromStay(String stayId, int restId) throws RestaurantNotFoundException {
		Query query = new Query(Criteria.where(Constants.STAYID).is(stayId).and(Constants.REST_RESTID).is(restId));
		if (mongoTemplate.exists(query, StayDetail.class)) {
			Update update = new Update().pull(Constants.RESTAURANTS,
					Query.query(Criteria.where(Constants.RESTID).is(restId)));
			mongoTemplate.updateFirst(query, update, StayDetail.class);
			Query fetchQuery = new Query(Criteria.where(Constants.STAYID).is(stayId));
			StayDetail stayDetail = mongoTemplate.findOne(fetchQuery, StayDetail.class);
			if (stayDetail.getRestaurants().isEmpty() || stayDetail.getRestaurants() == null) {
				Update stayUpdate = new Update().set(Constants.HAS_RESTAURANT, false);
				update.set(Constants.UPDATED_DATE, LocalDate.now());
				mongoTemplate.updateFirst(fetchQuery, stayUpdate, StayDetail.class);
			}
		} else {
			throw new RestaurantNotFoundException("Restaurant with given id doesn't exists in the stay :: " + restId);
		}
	}

	@Override
	public void updateRoomFacilities(String stayId, int roomId, Set<String> roomFacilities)
			throws RoomDetailNotFoundException {
		Query query = new Query(Criteria.where(Constants.STAYID).is(stayId).and(Constants.ROOMID).is(roomId));
		if (mongoTemplate.exists(query, StayDetail.class)) {
			Update update = new Update().addToSet(Constants.ROOM_FACILITIES).each(roomFacilities.toArray());
			mongoTemplate.updateFirst(query, update, StayDetail.class);
		} else {
			throw new RoomDetailNotFoundException(
					"Room with given id doesn't exists :: " + roomId + " at stay ::" + stayId);
		}
	}

	@Override
	public StayDetail updateStay(String stayId, Map<String, Object> updatedFields) throws StayNotFoundException {
		Query query = new Query(Criteria.where(Constants.STAYID).is(stayId));
		Update update = new Update().set(Constants.UPDATED_DATE, LocalDate.now());
		if (updatedFields.containsKey(Constants.STAY_FACILITIES)) {
			update.addToSet(Constants.STAY_FACILITIES).each(updatedFields.get(Constants.STAY_FACILITIES));
			updatedFields.remove(Constants.STAY_FACILITIES);
		}
		if (updatedFields.containsKey(Constants.STAY_RULES)) {
			update.addToSet(Constants.STAY_RULES).each(updatedFields.get(Constants.STAY_RULES));
			updatedFields.remove(Constants.STAY_RULES);
		}
		updatedFields.entrySet().stream().filter(entry -> !Constants.STAY_IMMUTABLE_FIELDS.contains(entry.getKey()))
				.forEach(entry -> update.set(entry.getKey(), entry.getValue()));
		mongoTemplate.updateFirst(query, update, StayDetail.class);
		Query fetchQuery = new Query(Criteria.where(Constants.STAYID).is(stayId));
		StayDetail stayDetail = mongoTemplate.findOne(fetchQuery, StayDetail.class);
		return stayDetail;
	}

	@Override
	public void updateRoomPrice(String stayId, int roomId, double price) throws RoomDetailNotFoundException {
		Query query = new Query(Criteria.where(Constants.STAYID).is(stayId).and(Constants.ROOMID).is(roomId));
		if (mongoTemplate.exists(query, StayDetail.class)) {
			Update update = new Update().set(Constants.ROOM_PRICE, price);
			mongoTemplate.updateFirst(query, update, StayDetail.class);
		} else {
			throw new RoomDetailNotFoundException(
					"Room with given id doesn't exists :: " + roomId + " at stay ::" + stayId);
		}
	}

}
