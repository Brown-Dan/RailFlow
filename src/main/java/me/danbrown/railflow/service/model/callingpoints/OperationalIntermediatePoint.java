package me.danbrown.railflow.service.model.callingpoints;

import lombok.Builder;
import me.danbrown.railflow.repository.model.RoutePointEntity;
import me.danbrown.railflow.service.model.xml.StationXml;

import java.time.LocalTime;

import static me.danbrown.railflow.utils.MappingUtils.stringToLocalTime;

@Builder(setterPrefix = "with")
public record OperationalIntermediatePoint(ScheduleAttributes scheduleAttributes,
                                           LocalTime workingScheduledTimeOfArrival,
                                           LocalTime workingScheduledTimeOfDeparture,
                                           int delayMinutes) implements RoutePoint {

    public static OperationalIntermediatePoint fromXml(StationXml stationXml) {
        return OperationalIntermediatePoint.builder()
                .withScheduleAttributes(ScheduleAttributes.fromXml(stationXml))
                .withDelayMinutes(stationXml.getRdelay())
                .withWorkingScheduledTimeOfArrival(stringToLocalTime(stationXml.getWta()))
                .withWorkingScheduledTimeOfDeparture(stringToLocalTime(stationXml.getWtd()))
                .build();
    }

    public RoutePointEntity toEntity() {
        RoutePointEntity.RoutePointEntityBuilder builder = RoutePointEntity.builder();
        scheduleAttributes.addToEntityBuilder(builder);
        return builder.withWorkingTimeOfArrival(workingScheduledTimeOfArrival)
                .withWorkingTimeOfDeparture(workingScheduledTimeOfDeparture)
                .withDelayMinutes(delayMinutes)
                .build();
    }
}
