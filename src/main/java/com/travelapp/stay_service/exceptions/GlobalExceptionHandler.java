package com.travelapp.stay_service.exceptions;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(exception = StayNotFoundException.class)
    public ResponseEntity<String> handleStayNotFoundException(StayNotFoundException stayNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(stayNotFoundException.getMessage());
    }

    @ExceptionHandler(exception = DuplicateRestaurantException.class)
    public ResponseEntity<String> handleDuplicateRestaurantException(
            DuplicateRestaurantException duplicateRestaurantException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(duplicateRestaurantException.getMessage());
    }

    @ExceptionHandler(exception = RestaurantNotFoundException.class)
    public ResponseEntity<String> handleRestaurantNotFoundException(
            RestaurantNotFoundException restaurantNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(restaurantNotFoundException.getMessage());
    }

    @ExceptionHandler(exception = RoomDetailNotFoundException.class)
    public ResponseEntity<String> handleRoomDetailNotFoundException(
            RoomDetailNotFoundException roomDetailNotFoundException) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(roomDetailNotFoundException.getMessage());
    }

    @ExceptionHandler(exception = DuplicateKeyException.class)
    public ResponseEntity<String> handleDuplicateKeyException(DuplicateKeyException duplicateKeyException) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(duplicateKeyException.getMessage());
    }

    @ExceptionHandler(exception = InvalidDataException.class)
    public ResponseEntity<String> handleInvalidDataException(InvalidDataException invalidDataException) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(invalidDataException.getMessage());
    }
}
