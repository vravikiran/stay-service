package com.travelapp.stay_service.services;

import java.time.LocalDate;
import java.util.Set;
import java.util.Optional;
import java.util.Map;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


import com.travelapp.stay_service.exceptions.StayNotFoundException;
import com.travelapp.stay_service.exceptions.InvalidDataException;
import com.travelapp.stay_service.exceptions.RoomDetailNotFoundException;
import com.travelapp.stay_service.exceptions.RestaurantNotFoundException;
import com.travelapp.stay_service.exceptions.DuplicateRestaurantException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.travelapp.stay_service.entities.Restaurant;
import com.travelapp.stay_service.entities.StayDetail;
import com.travelapp.stay_service.enums.PropertyTypeEnum;
import com.travelapp.stay_service.repositories.StayDetailRepository;

@Service
public class StayDetailService {
    @Autowired
    StayDetailRepository stayDetailRepository;

    public StayDetail createStayDetail(StayDetail stayDetail) throws DuplicateKeyException {
        if (stayDetail.getPropertyType().name().toUpperCase().equals(PropertyTypeEnum.HOMESTAY.name())) {
            stayDetail.setApproved(false);
            stayDetail.setIsactive(false);
        }
        if (stayDetail.getRestaurants() != null && !stayDetail.getRestaurants().isEmpty()) {
            stayDetail.setHasRestaurant(true);
            Set<Restaurant> restaurants = stayDetail.getRestaurants().stream().map(rest -> {
                rest.setCreatedDate(LocalDate.now());
                rest.setUpdatedDate(LocalDate.now());
                return rest;
            }).collect(Collectors.toSet());
            stayDetail.setRestaurants(restaurants);
        }
        stayDetail.setCreatedDate(LocalDate.now());
        stayDetail.setUpdatedDate(LocalDate.now());
        try {
            return stayDetailRepository.save(stayDetail);
        } catch (DuplicateKeyException exception) {
            System.out.println("exception :: " + exception.getMessage());
            throw new DuplicateKeyException("A stay already exists at given address/same name and address");
        }
    }

    public void deactivateStay(String id) throws StayNotFoundException {
        Optional<StayDetail> stayDetailOpt = stayDetailRepository.findById(id);
        if (stayDetailOpt.isPresent()) {
            StayDetail stayDetail = stayDetailOpt.get();
            stayDetail.setIsactive(false);
            stayDetail.setUpdatedDate(LocalDate.now());
            stayDetailRepository.save(stayDetail);
        } else {
            throw new StayNotFoundException("No stay found with given id :: " + id);
        }
    }

    public void activateStay(String id) throws StayNotFoundException {
        Optional<StayDetail> stayDetailOpt = stayDetailRepository.findById(id);
        if (stayDetailOpt.isPresent()) {
            StayDetail stayDetail = stayDetailOpt.get();
            stayDetail.setUpdatedDate(LocalDate.now());
            stayDetail.setIsactive(true);
            stayDetailRepository.save(stayDetail);
        } else {
            throw new StayNotFoundException("No stay found with given id :: " + id);
        }

    }

    public StayDetail getStayDetail(String id) throws StayNotFoundException {
        Optional<StayDetail> stayDetailOpt = stayDetailRepository.findById(id);
        if (stayDetailOpt.isPresent()) {
            return stayDetailOpt.get();
        } else {
            throw new StayNotFoundException("No stay found with given id :: " + id);
        }
    }

    public Page<StayDetail> searchStays(String city, String location, String subLocation, String propertyType,
                                        String propertyRating, String userRating, int page, int size) {
        return stayDetailRepository.searchStays(city, location, subLocation, propertyType, propertyRating, userRating,
                page, size);
    }

    public void approveHomeStay(String id) throws StayNotFoundException, InvalidDataException {
        Optional<StayDetail> stayDetailOpt = stayDetailRepository.findById(id);
        if (stayDetailOpt.isPresent()) {
            StayDetail stayDetail = stayDetailOpt.get();
            if (stayDetail.getPropertyType().equals(PropertyTypeEnum.HOMESTAY)) {
                stayDetail.setApproved(true);
                stayDetail.setIsactive(true);
                stayDetail.setUpdatedDate(LocalDate.now());
                stayDetailRepository.save(stayDetail);
            } else {
                throw new InvalidDataException("Not a home stay");
            }
        } else {
            throw new StayNotFoundException("No stay found with given id :: " + id);
        }
    }

    public StayDetail updateRestaurantDetailsAtStay(String stayId, int restId, Map<String, Object> updatedFields)
            throws StayNotFoundException, RestaurantNotFoundException {
        if (stayDetailRepository.existsById(stayId)) {
            return stayDetailRepository.updateRestaurantDetailsAtStay(stayId, restId, updatedFields);
        } else {
            throw new StayNotFoundException("No stay found with given id :: " + stayId);
        }
    }

    public StayDetail addNewRestaurantToStay(String stayId, Restaurant restaurant)
            throws StayNotFoundException, DuplicateRestaurantException {
        Optional<StayDetail> stayDetailOpt = stayDetailRepository.findById(stayId);
        if (stayDetailOpt.isPresent()) {
            StayDetail stayDetail = stayDetailOpt.get();
            Set<Restaurant> restaurants = null;
            if (verifyUniqueRestaurantNameInStay(stayDetail, restaurant.getName())
                    || (stayDetail.getRestaurants() != null && stayDetail.getRestaurants().contains(restaurant))) {
                throw new DuplicateRestaurantException("Restaurant already exists at stay with given details");
            }
            if (stayDetail.getRestaurants() != null) {
                restaurants = stayDetail.getRestaurants();
                restaurants.add(restaurant);
                stayDetail.setHasRestaurant(true);

            } else {
                restaurants = new HashSet<>();
                restaurants.add(restaurant);
            }
            stayDetail.setRestaurants(restaurants);
            stayDetail.setUpdatedDate(LocalDate.now());
            return stayDetailRepository.save(stayDetail);
        } else {
            throw new StayNotFoundException("No stay found with given id :: " + stayId);
        }
    }

    public void removeRestaurantFromStay(String stayId, int restId)
            throws RestaurantNotFoundException, StayNotFoundException {
        if (stayDetailRepository.existsById(stayId)) {
            stayDetailRepository.removeRestuarantFromStay(stayId, restId);
        } else {
            throw new StayNotFoundException("No stay found with given id :: " + stayId);
        }
    }

    public void updateRoomFacilities(String stayId, int roomId, Set<String> roomFacilities)
            throws StayNotFoundException, RoomDetailNotFoundException {
        if (stayDetailRepository.existsById(stayId)) {
            stayDetailRepository.updateRoomFacilities(stayId, roomId, roomFacilities);
        } else {
            throw new StayNotFoundException("No stay found with given id :: " + stayId);
        }
    }

    public StayDetail updateStay(String stayId, Map<String, Object> updatedFields)
            throws StayNotFoundException, DuplicateKeyException {
        StayDetail stayDetail = null;
        if (stayDetailRepository.existsById(stayId)) {
            try {
                stayDetail = stayDetailRepository.updateStay(stayId, updatedFields);
            } catch (DuplicateKeyException duplicateKeyException) {
                throw new DuplicateKeyException("A stay already exists with given name and address");
            }
            return stayDetail;
        } else {
            throw new StayNotFoundException("No stay found with given id :: " + stayId);
        }
    }

    public void updateRoomPrice(String stayId, int roomId, double price)
            throws StayNotFoundException, RoomDetailNotFoundException {
        if (stayDetailRepository.existsById(stayId)) {
            stayDetailRepository.updateRoomPrice(stayId, roomId, price);
        } else {
            throw new StayNotFoundException("No stay found with given id :: " + stayId);
        }
    }

    public List<StayDetail> searchStays(String location) {
        return stayDetailRepository.search(location);
    }

    private boolean verifyUniqueRestaurantNameInStay(StayDetail stayDetail, String name) {
        boolean isExists = false;
        Set<Restaurant> restaurants = stayDetail.getRestaurants();
        isExists = restaurants.stream().anyMatch(rest -> rest.getName().equalsIgnoreCase(name));
        System.out.println(isExists);
        return isExists;
    }

}
