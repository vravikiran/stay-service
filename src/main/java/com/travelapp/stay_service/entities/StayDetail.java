package com.travelapp.stay_service.entities;

import java.time.LocalDate;
import java.util.Set;

import com.travelapp.stay_service.util.EnumValidator;
import jakarta.validation.Valid;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.travelapp.stay_service.enums.PropertyRatingEnum;
import com.travelapp.stay_service.enums.PropertyTypeEnum;
import com.travelapp.stay_service.enums.UserRatingEnum;
@Data
@Document(collection = "StayDetail")
public class StayDetail {
    @Transient
    public static final String SEQUENCE_NAME = "stay_sequence";
    @Id
    private long id;
    private String name;
    @Valid
    private Set<RoomDetail> rooms;
    private boolean hasRestaurant = false;
    private boolean hasGym = false;
    private boolean hasSwimPool = false;
    private Address address;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String stayImagesUri;
    @EnumValidator(enumClazz = PropertyTypeEnum.class, message = "Invalid property type")
    private String propertyType;
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @EnumValidator(enumClazz = PropertyRatingEnum.class, message = "Invalid property rating")
    private String propertyRating;
    @EnumValidator(enumClazz = UserRatingEnum.class, message = "Invalid user rating")
    private String userRating;
    @Valid
    private Set<Restaurant> restaurants;
    private boolean isactive = true;
    private boolean isApproved = true;
    private Set<String> facilities;
    private Set<String> rules;
    @JsonIgnore
    private LocalDate createdDate;
    @JsonIgnore
    private LocalDate updatedDate;
}
