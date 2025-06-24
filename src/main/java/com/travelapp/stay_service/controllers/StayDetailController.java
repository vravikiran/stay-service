package com.travelapp.stay_service.controllers;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.travelapp.stay_service.entities.Restaurant;
import com.travelapp.stay_service.entities.StayDetail;
import com.travelapp.stay_service.exceptions.DuplicateRestaurantException;
import com.travelapp.stay_service.exceptions.RestaurantNotFoundException;
import com.travelapp.stay_service.exceptions.RoomDetailNotFoundException;
import com.travelapp.stay_service.exceptions.StayNotFoundException;
import com.travelapp.stay_service.services.StayDetailService;

@RestController
@RequestMapping("/stay")
public class StayDetailController {
	@Autowired
	StayDetailService stayDetailService;

	@PostMapping
	public ResponseEntity<StayDetail> createStay(@RequestBody StayDetail stayDetail) throws DuplicateKeyException {
		StayDetail createdStay = stayDetailService.createStayDetail(stayDetail);
		return ResponseEntity.ok(createdStay);
	}

	@PatchMapping("/activate")
	public ResponseEntity<String> makeStayOperational(@RequestParam String id) {
		stayDetailService.activateStay(id);
		return ResponseEntity.ok("Stay operational for business");
	}

	@PatchMapping("/deactivate")
	public ResponseEntity<String> suspendOpOfStay(@RequestParam String id) {
		stayDetailService.deactivateStay(id);
		return ResponseEntity.ok("Stay operations suspensed/terminated successfully");
	}

	@GetMapping("/detail/id")
	public ResponseEntity<StayDetail> getStayDetails(@RequestParam String id) {
		StayDetail stayDetail = stayDetailService.getStayDetail(id);
		return ResponseEntity.ok(stayDetail);
	}

	@GetMapping("/search")
	public ResponseEntity<Page<StayDetail>> searchStays(@RequestParam String city,
			@RequestParam(defaultValue = "ALL", required = false) String propertyType,
			@RequestParam(defaultValue = "0", required = false) int page,
			@RequestParam(defaultValue = "5", required = false) int size,
			@RequestParam(required = false) String location, @RequestParam(required = false) String subLocation,
			@RequestParam(required = false) String propertyRating, @RequestParam(required = false) String userRating) {
		Page<StayDetail> stays = stayDetailService.searchStays(city, location, subLocation, propertyType,
				propertyRating, userRating, page, size);
		return ResponseEntity.ok(stays);
	}

	@PatchMapping("/approve")
	public ResponseEntity<String> approveHomeStay(@RequestParam String id) {
		stayDetailService.approveHomeStay(id);
		return ResponseEntity.ok("Home stay approved successfully");
	}

	@PatchMapping("/update/restaurant")
	public ResponseEntity<StayDetail> updateRestaurantDetailsAtStay(@RequestParam(required = true) int restId,
			@RequestParam(required = true) String stayId, @RequestBody Map<String, Object> updatedFields)
			throws StayNotFoundException, RestaurantNotFoundException {
		StayDetail stayDetail = stayDetailService.updateRestaurantDetailsAtStay(stayId, restId, updatedFields);
		return ResponseEntity.ok(stayDetail);
	}

	@PatchMapping("/add/restaurant")
	public ResponseEntity<StayDetail> addNewRestaurantAtStay(@RequestParam String stayId,
			@RequestBody Restaurant restaurant) throws StayNotFoundException, DuplicateRestaurantException {
		StayDetail stayDetail = stayDetailService.addNewRestaurantToStay(stayId, restaurant);
		return ResponseEntity.ok(stayDetail);
	}

	@DeleteMapping("/remove/restuarant")
	public ResponseEntity<String> removeRestuarantFromStay(@RequestParam String stayId, @RequestParam int restId)
			throws RestaurantNotFoundException, StayNotFoundException {
		stayDetailService.removeRestuarantFromStay(stayId, restId);
		return ResponseEntity.ok("Restaurant removed successfully from stay");
	}

	@PatchMapping("/room/facilities")
	public ResponseEntity<String> updateRoomFacilities(@RequestParam String stayId, @RequestParam int roomId,
			@RequestBody Set<String> roomFacilities) throws StayNotFoundException, RoomDetailNotFoundException {
		stayDetailService.updateRoomFacilities(stayId, roomId, roomFacilities);
		return ResponseEntity.ok("Room facilities are updated sucessfully");
	}

	@PatchMapping("/update")
	public ResponseEntity<StayDetail> updateStay(@RequestParam String stayId,
			@RequestBody Map<String, Object> updatedFields) throws StayNotFoundException, DuplicateKeyException {
		StayDetail stayDetail = stayDetailService.updateStay(stayId, updatedFields);
		return ResponseEntity.ok(stayDetail);
	}
	
	@PatchMapping("/room/price/update")
	public ResponseEntity<String> updateRoomPrice(@RequestParam String stayId, @RequestParam int roomId,
			@RequestParam double price) throws StayNotFoundException, RoomDetailNotFoundException {
		stayDetailService.updateRoomPrice(stayId, roomId, price);
		return ResponseEntity.ok("Room price updated successfully");
	}
}
