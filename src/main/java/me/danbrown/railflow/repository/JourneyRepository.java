package me.danbrown.railflow.repository;

import me.danbrown.railflow.repository.model.JourneyEntity;
import me.danbrown.railflow.repository.model.RoutePointEntity;
import me.danbrown.railflow.service.model.Journey;
import me.danbrown.railflow.service.model.callingpoints.*;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static uk.co.railflow.generated.Tables.JOURNEY;
import static uk.co.railflow.generated.Tables.ROUTE_POINT;


@Component
public class JourneyRepository {

    private final DSLContext db;

    public JourneyRepository(DSLContext db) {
        this.db = db;
    }

    public void insertJourney(Journey journey) {
        JourneyEntity journeyEntity = mapJourneyToJourneyEntity(journey);

        db.insertInto(JOURNEY)
                .set(JOURNEY.TRAIN_ID, journeyEntity.trainId())
                .set(JOURNEY.SCHEDULED_START_DATE, journeyEntity.scheduledStartDate())
                .execute();
        insertRoutePoints(journeyEntity.routePointEntities(), journeyEntity.trainId());
    }

    public Optional<Journey> fetchJourneyByTrainId(String trainId) {
        Record journeyRecord = db.select()
                .from(JOURNEY)
                .where(JOURNEY.TRAIN_ID.eq(trainId))
                .fetchOne();

        if (journeyRecord == null) {
            return Optional.empty();
        }

        List<RoutePointEntity> routePoints = db.select()
                .from(ROUTE_POINT)
                .where(ROUTE_POINT.TRAIN_ID.eq(trainId))
                .orderBy(ROUTE_POINT.POSITION.asc())
                .fetchInto(RoutePointEntity.class);

        return Optional.of(mapJourneyEntityToJourney(JourneyEntity.builder()
                .withTrainId(journeyRecord.get(JOURNEY.TRAIN_ID))
                .withScheduledStartDate(journeyRecord.get(JOURNEY.SCHEDULED_START_DATE))
                .withRoutePointEntities(routePoints)
                .build()));
    }

    private void insertRoutePoints(List<RoutePointEntity> routePointEntities, String trainId) {
        for (int i = 0; i < routePointEntities.size(); i++) {
            RoutePointEntity routePointEntity = routePointEntities.get(i);
            db.insertInto(ROUTE_POINT)
                    .set(ROUTE_POINT.TRAIN_ID, trainId)
                    .set(ROUTE_POINT.ROUTE_POINT_TYPE, routePointEntity.routePointType().name())
                    .set(ROUTE_POINT.POSITION, i)
                    .set(ROUTE_POINT.TIPLOC, routePointEntity.tiploc())
                    .set(ROUTE_POINT.ACTIVITY_TYPE, routePointEntity.activityType())
                    .set(ROUTE_POINT.PLANNED_ACTIVITY_TYPE, routePointEntity.plannedActivityType())
                    .set(ROUTE_POINT.IS_CANCELLED, routePointEntity.isCancelled())
                    .set(ROUTE_POINT.PLATFORM, routePointEntity.platform())
                    .set(ROUTE_POINT.PLANNED_TIME_OF_ARRIVAL, routePointEntity.plannedTimeOfArrival())
                    .set(ROUTE_POINT.PLANNED_TIME_OF_DEPARTURE, routePointEntity.plannedTimeOfDeparture())
                    .set(ROUTE_POINT.WORKING_TIME_OF_ARRIVAL, routePointEntity.workingTimeOfArrival())
                    .set(ROUTE_POINT.WORKING_TIME_OF_DEPARTURE, routePointEntity.workingTimeOfDeparture())
                    .set(ROUTE_POINT.FALSE_DESTINATION, routePointEntity.falseDestination())
                    .set(ROUTE_POINT.DELAY_MINUTES, routePointEntity.delayMinutes())
                    .set(ROUTE_POINT.WORKING_TIME_OF_PASSING, routePointEntity.workingTimeOfPassing())
                    .execute();
        }
    }

    private JourneyEntity mapJourneyToJourneyEntity(Journey journey) {
        return JourneyEntity.builder()
                .withTrainId(journey.trainId())
                .withScheduledStartDate(journey.scheduledStartDate())
                .withRoutePointEntities(journey.route().stream().map(RoutePoint::toEntity).toList())
                .build();
    }

    private Journey mapJourneyEntityToJourney(JourneyEntity journeyEntity) {
        return Journey.builder()
                .withTrainId(journeyEntity.trainId())
                .withScheduledStartDate(journeyEntity.scheduledStartDate())
                .withRoute(journeyEntity.routePointEntities().stream().map(this::mapRoutePointEntityToRoutePoint).toList())
                .build();
    }

    private RoutePoint mapRoutePointEntityToRoutePoint(RoutePointEntity routePointEntity) {
        return switch (routePointEntity.routePointType()) {
            case OR -> PassengerOriginPoint.fromEntity(routePointEntity);
            case OPOR -> OperationalOriginPoint.fromEntity(routePointEntity);
            case IP -> PassengerIntermediatePoint.fromEntity(routePointEntity);
            case OPIP -> OperationalIntermediatePoint.fromEntity(routePointEntity);
            case PP -> IntermediatePassingPoint.fromEntity(routePointEntity);
            case DT -> PassengerDestinationPoint.fromEntity(routePointEntity);
            case OPDT -> OperationalDestinationPoint.fromEntity(routePointEntity);
        };
    }
}
