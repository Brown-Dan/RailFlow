package me.danbrown.railflow.service.model.callingpoints;

import lombok.Builder;
import me.danbrown.railflow.repository.model.RoutePointEntity;
import me.danbrown.railflow.service.model.xml.StationXml;

import java.time.LocalTime;

import static me.danbrown.railflow.utils.MappingUtils.stringToLocalTime;

@Builder(setterPrefix = "with")
public record PassengerIntermediatePoint(ScheduleAttributes scheduleAttributes,
                                         CallingPointAttributes callingPointAttributes,
                                         LocalTime workingScheduledTimeOfArrival,
                                         LocalTime workingScheduledTimeOfDeparture,
                                         int delayMinutes,
                                         String falseDestinationTiploc) implements RoutePoint {
    public static RoutePoint fromXml(StationXml stationXml) {
        return PassengerIntermediatePoint.builder()
                .withScheduleAttributes(ScheduleAttributes.fromXml(stationXml))
                .withCallingPointAttributes(CallingPointAttributes.fromXml(stationXml))
                .withWorkingScheduledTimeOfArrival(stringToLocalTime(stationXml.getWta()))
                .withWorkingScheduledTimeOfDeparture(stringToLocalTime(stationXml.getWtd()))
                .withDelayMinutes(stationXml.getRdelay())
                .withFalseDestinationTiploc(stationXml.getFd())
                .build();
    }

    @Override
    public RoutePointType routePointType() {
        return RoutePointType.PP;
    }

    public RoutePointEntity toEntity() {
        RoutePointEntity.RoutePointEntityBuilder builder = RoutePointEntity.builder();
        scheduleAttributes.addToEntityBuilder(builder);
        callingPointAttributes.addToEntityBuilder(builder);
        return builder.withWorkingTimeOfArrival(workingScheduledTimeOfArrival)
                .withWorkingTimeOfDeparture(workingScheduledTimeOfDeparture)
                .withDelayMinutes(delayMinutes)
                .withFalseDestination(falseDestinationTiploc)
                .withRoutePointType(RoutePointType.PP)
                .build();
    }

    public static PassengerIntermediatePoint fromEntity(RoutePointEntity routePointEntity) {
        return PassengerIntermediatePoint.builder()
                .withScheduleAttributes(ScheduleAttributes.fromEntity(routePointEntity))
                .withCallingPointAttributes(CallingPointAttributes.fromEntity(routePointEntity))
                .withWorkingScheduledTimeOfArrival(routePointEntity.workingTimeOfArrival())
                .withWorkingScheduledTimeOfDeparture(routePointEntity.workingTimeOfDeparture())
                .withDelayMinutes(routePointEntity.delayMinutes())
                .withFalseDestinationTiploc(routePointEntity.falseDestination())
                .build();
    }
}
