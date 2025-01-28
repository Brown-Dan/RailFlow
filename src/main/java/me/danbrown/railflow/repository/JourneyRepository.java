package me.danbrown.railflow.repository;

import me.danbrown.railflow.service.model.Destination;
import me.danbrown.railflow.service.model.Journey;
import me.danbrown.railflow.service.model.Origin;
import org.jooq.DSLContext;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

import static uk.co.railflow.generated.Tables.JOURNEY;
import static uk.co.railflow.generated.Tables.STATION;


@Component
public class JourneyRepository {


    private final DSLContext db;

    public JourneyRepository(DSLContext db) {
        this.db = db;
    }

    public Optional<Journey> fetchJourneyById(String trainId) {
        return db.select(JOURNEY.TRAIN_ID, JOURNEY.SCHEDULED_START_DATE, JOURNEY.ORIGIN, JOURNEY.DESTINATION)
                .from(JOURNEY)
                .where(JOURNEY.TRAIN_ID.eq(trainId))
                .fetchOptionalInto(uk.co.railflow.generated.tables.pojos.Journey.class)
                .map(journey -> {
                    Journey.JourneyBuilder journeyBuilder = Journey.builder();
                    if (journey.getOrigin() != null) {
                        Origin origin = db.select(STATION.TIPLOC, STATION.SCHEDULED_DEPARTURE_TIME)
                                .from(STATION)
                                .where(STATION.ID.eq(journey.getOrigin()))
                                .fetchSingleInto(Origin.class);
                        journeyBuilder.origin(origin);
                    };

                    if (journey.getDestination() != null) {
                        Destination destination = db.select(STATION.TIPLOC, STATION.SCHEDULED_ARRIVAL_TIME)
                                .from(STATION)
                                .where(STATION.ID.eq(journey.getDestination()))
                                .fetchSingleInto(Destination.class);
                        journeyBuilder.destination(destination);
                    };

                    return journeyBuilder
                            .trainId(journey.getTrainId())
                            .scheduledStartDate(journey.getScheduledStartDate())
                            .build();
                });
    }

    public void insertJourney(Journey journey) {
        UUID originId = insertOrigin(journey.origin());
        UUID destinationId = insertDestination(journey.destination());

        db.insertInto(JOURNEY)
                .set(JOURNEY.TRAIN_ID, journey.trainId())
                .set(JOURNEY.SCHEDULED_START_DATE, journey.scheduledStartDate())
                .set(JOURNEY.ORIGIN, originId)
                .set(JOURNEY.DESTINATION, destinationId)
                .execute();
    }

    private UUID insertOrigin(Origin origin) {
        UUID id = UUID.randomUUID();
        db.insertInto(STATION)
                .set(STATION.ID, id)
                .set(STATION.SCHEDULED_DEPARTURE_TIME, origin.scheduledDepartureTime())
                .set(STATION.TIPLOC, origin.tiploc())
                .execute();
        return id;
    }

    private UUID insertDestination(Destination destination) {
        UUID id = UUID.randomUUID();
        db.insertInto(STATION)
                .set(STATION.ID, id)
                .set(STATION.SCHEDULED_ARRIVAL_TIME, destination.scheduledArrivalTime())
                .set(STATION.TIPLOC, destination.tiploc())
                .execute();
        return id;
    }
}
