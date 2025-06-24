package com.travelapp.stay_service.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.travelapp.stay_service.entities.StayDetail;

@Repository
public interface StayDetailRepository extends MongoRepository<StayDetail, String>, StayDetailCustomRepository {

}
