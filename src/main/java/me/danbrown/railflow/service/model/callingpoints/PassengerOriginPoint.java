package me.danbrown.railflow.service.model.callingpoints;

import lombok.Builder;
import me.danbrown.railflow.repository.model.RoutePointEntity;
import me.danbrown.railflow.service.model.xml.StationXml;

import java.time.LocalTime;

import static me.danbrown.railflow.utils.MappingUtils.stringToLocalTime;

@Builder(setterPrefix = "with")
public record PassengerOriginPoint(ScheduleAttributes scheduleAttributes,
                                   CallingPointAttributes callingPointAttributes,
                                   LocalTime workingScheduledTimeOfArrival,
                                   LocalTime workingScheduledTimeOfDeparture,
                                   String falseDestinationTiploc) implements RoutePoint {

    public static PassengerOriginPoint fromXml(StationXml stationXml) {
        return PassengerOriginPoint.builder()
                .withScheduleAttributes(ScheduleAttributes.fromXml(stationXml))
                .withCallingPointAttributes(CallingPointAttributes.fromXml(stationXml))
                .withWorkingScheduledTimeOfArrival(stringToLocalTime(stationXml.getWta()))
                .withWorkingScheduledTimeOfDeparture(stringToLocalTime(stationXml.getWtd()))
                .withFalseDestinationTiploc(stationXml.getFd())
                .build();
    }

    public RoutePointEntity toEntity() {
        RoutePointEntity.RoutePointEntityBuilder builder = RoutePointEntity.builder();
        scheduleAttributes.addToEntityBuilder(builder);
        callingPointAttributes.addToEntityBuilder(builder);
        return builder.withWorkingTimeOfArrival(workingScheduledTimeOfArrival)
                .withWorkingTimeOfDeparture(workingScheduledTimeOfDeparture)
                .withFalseDestination(falseDestinationTiploc)
                .build();
    }
}
