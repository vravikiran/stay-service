package com.travelapp.stay_service.config;

import com.mongodb.lang.NonNull;
import com.travelapp.stay_service.entities.StayDetail;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertCallback;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class MongoValidationCallback implements BeforeConvertCallback<StayDetail> {

    private final Validator validator;

    public MongoValidationCallback(Validator validator) {
       this.validator = validator;
    }

    @NonNull
    @Override
    public StayDetail onBeforeConvert(@NonNull StayDetail entity, @NonNull String collection) {
        Set<ConstraintViolation<Object>> violations = validator.validate(entity);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        return entity;
    }
}
