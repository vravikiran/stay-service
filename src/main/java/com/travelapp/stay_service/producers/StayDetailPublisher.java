package com.travelapp.stay_service.producers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.travelapp.stay_service.entities.StayDetail;
import com.travelapp.stay_service.mapper.StayDetailMapper;
import com.travelapp.stay_service.model.StayDetailDto;
import com.travelapp.stay_service.util.Constants;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StayDetailPublisher {
    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper;
    private final StayDetailMapper stayDetailMapper;
    Logger logger = LoggerFactory.getLogger(StayDetailPublisher.class);

    @Async
    public void publish(StayDetail stayDetail) throws JsonProcessingException {
        StayDetailDto stayDetailDto = stayDetailMapper.stayDetailToStayDetailDto(stayDetail);
        ProducerRecord<String ,String> producerRecord = new ProducerRecord<>(Constants.STAY_DETAIL_TOPIC,objectMapper.writeValueAsString(stayDetailDto));
        kafkaTemplate.send(producerRecord);
        logger.info("stay detail published successfully :: {}", stayDetailDto);
    }
}
