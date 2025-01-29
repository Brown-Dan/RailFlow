package me.danbrown.railflow.service.model.callingpoints;

import lombok.Builder;
import me.danbrown.railflow.repository.model.RoutePointEntity;
import me.danbrown.railflow.service.model.xml.StationXml;

import java.time.LocalTime;

import static me.danbrown.railflow.utils.MappingUtils.stringToLocalTime;

@Builder(setterPrefix = "with")
public record PassengerDestinationPoint(ScheduleAttributes scheduleAttributes,
                                        CallingPointAttributes callingPointAttributes,
                                        LocalTime workingScheduledTimeOfArrival,
                                        LocalTime workingScheduledTimeOfDeparture,
                                        int delayMinutes) implements RoutePoint {

    public static PassengerDestinationPoint fromXml(StationXml stationXml) {
        return PassengerDestinationPoint.builder()
                .withScheduleAttributes(ScheduleAttributes.fromXml(stationXml))
                .withCallingPointAttributes(CallingPointAttributes.fromXml(stationXml))
                .withDelayMinutes(stationXml.getRdelay())
                .withWorkingScheduledTimeOfArrival(stringToLocalTime(stationXml.getWta()))
                .withWorkingScheduledTimeOfDeparture(stringToLocalTime(stationXml.getWtd()))
                .build();
    }

    @Override
    public RoutePointType routePointType() {
        return RoutePointType.DT;
    }

    public RoutePointEntity toEntity() {
        RoutePointEntity.RoutePointEntityBuilder builder = RoutePointEntity.builder();
        scheduleAttributes.addToEntityBuilder(builder);
        callingPointAttributes.addToEntityBuilder(builder);
        return builder.withWorkingTimeOfArrival(workingScheduledTimeOfArrival)
                .withWorkingTimeOfDeparture(workingScheduledTimeOfDeparture)
                .withDelayMinutes(delayMinutes)
                .withRoutePointType(RoutePointType.DT)
                .build();
    }

    public static PassengerDestinationPoint fromEntity(RoutePointEntity routePointEntity) {
        return PassengerDestinationPoint.builder()
                .withScheduleAttributes(ScheduleAttributes.fromEntity(routePointEntity))
                .withCallingPointAttributes(CallingPointAttributes.fromEntity(routePointEntity))
                .withWorkingScheduledTimeOfArrival(routePointEntity.workingTimeOfArrival())
                .withWorkingScheduledTimeOfDeparture(routePointEntity.workingTimeOfDeparture())
                .withDelayMinutes(routePointEntity.delayMinutes())
                .build();
    }
}
