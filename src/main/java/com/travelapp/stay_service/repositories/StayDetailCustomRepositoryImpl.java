package com.travelapp.stay_service.repositories;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

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

    @Override
    public Page<StayDetail> searchStays(String city, String location, String subLocation, String propertyType,
                                        String propertyRating, String userRating, int page, int size) {
        Criteria criteria = Criteria.where(Constants.CITY)
                .regex("^" + Pattern.quote(city) + "$", "i");

        if (location != null && !location.isBlank()) {
            criteria.and(Constants.LOCATION)
                    .regex("^" + Pattern.quote(location) + "$", "i");
        }
        if (subLocation != null && !subLocation.isBlank()) {
            criteria.and(Constants.SUBLOCATION)
                    .regex("^" + Pattern.quote(subLocation) + "$", "i");
        }
        if (propertyType != null && !propertyType.isBlank()) {
            criteria.and(Constants.PROPERTYTYPE).is(PropertyTypeEnum.valueOf(propertyType.toUpperCase()).name());
        }
        if (propertyRating != null && !propertyRating.isBlank()) {
            criteria.and(Constants.PROPERTYRATING).is(PropertyRatingEnum.valueOf(propertyRating.toUpperCase()).name());
        }
        if (userRating != null && !userRating.isBlank()) {
            criteria.and(Constants.USERRATING).is(UserRatingEnum.valueOf(userRating.toUpperCase()).name());
        }

        Query query = new Query(criteria);
        query.with(PageRequest.of(page, size));
        List<StayDetail> stays = mongoTemplate.find(query, StayDetail.class);
        Map<Object, Set<Object>> stayWithRooms = stays.stream().collect(Collectors.groupingBy(StayDetail::getId, Collectors.mapping(StayDetail::getRooms, Collectors.toSet())));
        long count = mongoTemplate.count(Query.of(query).limit(-1).skip(-1), StayDetail.class);
        return new PageImpl<>(stays, PageRequest.of(page, size), count);
    }

	@Override
	public StayDetail updateRestaurantDetailsAtStay(Long stayId, int restId, Map<String, Object> updatedFields)
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
			updatedFields.forEach((key, value) -> update.set(Constants.REST_SUB_DOC + key, value));
			mongoTemplate.updateFirst(query, update, StayDetail.class);
			Query fetchQuery = new Query(Criteria.where(Constants.STAYID).is(stayId));
			return mongoTemplate.findOne(fetchQuery, StayDetail.class);
		} else {
			throw new RestaurantNotFoundException("Restaurant with given id doesn't exists in the stay :; " + restId);
		}
	}

	@Override
	public void removeRestuarantFromStay(Long stayId, int restId) throws RestaurantNotFoundException {
		Query query = new Query(Criteria.where(Constants.STAYID).is(stayId).and(Constants.REST_RESTID).is(restId));
		if (mongoTemplate.exists(query, StayDetail.class)) {
			Update update = new Update().pull(Constants.RESTAURANTS,
					Query.query(Criteria.where(Constants.RESTID).is(restId)));
			mongoTemplate.updateFirst(query, update, StayDetail.class);
			Query fetchQuery = new Query(Criteria.where(Constants.STAYID).is(stayId));
			StayDetail stayDetail = mongoTemplate.findOne(fetchQuery, StayDetail.class);
			if (stayDetail != null && stayDetail.getRestaurants().isEmpty()) {
				Update stayUpdate = new Update().set(Constants.HAS_RESTAURANT, false);
				update.set(Constants.UPDATED_DATE, LocalDate.now());
				mongoTemplate.updateFirst(fetchQuery, stayUpdate, StayDetail.class);
			}
		} else {
			throw new RestaurantNotFoundException("Restaurant with given id doesn't exists in the stay :: " + restId);
		}
	}

	@Override
	public void updateRoomFacilities(Long stayId, int roomId, Set<String> roomFacilities)
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
	public StayDetail updateStay(Long stayId, Map<String, Object> updatedFields) throws StayNotFoundException {
		Query query = new Query(Criteria.where(Constants.STAYID).is(stayId));
		if(mongoTemplate.exists(query,StayDetail.class)) {
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
			return mongoTemplate.findOne(fetchQuery, StayDetail.class);
		} else {
			throw new StayNotFoundException("Stay not fount with given id :: "+stayId);
		}
	}

	@Override
	public void updateRoomPrice(Long stayId, int roomId, double price) throws RoomDetailNotFoundException {
		Query query = new Query(Criteria.where(Constants.STAYID).is(stayId).and(Constants.ROOMID).is(roomId));
		if (mongoTemplate.exists(query, StayDetail.class)) {
			Update update = new Update().set(Constants.ROOM_PRICE, price);
			mongoTemplate.updateFirst(query, update, StayDetail.class);
		} else {
			throw new RoomDetailNotFoundException(
					"Room with given id doesn't exists :: " + roomId + " at stay ::" + stayId);
		}
	}

	@Override
	public List<StayDetail> search(String  textToSearch) {
		Document search = new Document("$search", new Document("compound",
				new Document("should", List.of(
						createAutocompleteStage("address.city", textToSearch),
						createAutocompleteStage("address.location", textToSearch),
						createAutocompleteStage("address.subLocation", textToSearch)
				))
		));
		Aggregation aggregation = Aggregation.newAggregation((context) -> search);
		return mongoTemplate.aggregate(aggregation, "StayDetail", StayDetail.class).getMappedResults();
	}

	private Document createAutocompleteStage(String path, String query) {
		return new Document("autocomplete", new Document("query", query).append("path", path));
	}

}
