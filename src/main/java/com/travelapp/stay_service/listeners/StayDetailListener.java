package com.travelapp.stay_service.listeners;

import com.travelapp.stay_service.entities.StayDetail;
import com.travelapp.stay_service.services.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;

@Component
public class StayDetailListener extends AbstractMongoEventListener<StayDetail> {

    private final SequenceGeneratorService sequenceGenerator;

    @Autowired
    public StayDetailListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<StayDetail> event) {
        if (event.getSource().getId() == 0) { // Only set if not already provided
            event.getSource().setId(sequenceGenerator.generateSequence(StayDetail.SEQUENCE_NAME));
        }
    }
}
