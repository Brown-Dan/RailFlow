package me.danbrown.railflow.repository.model;

import lombok.Builder;
import me.danbrown.railflow.service.model.callingpoints.RoutePointType;

import java.time.LocalTime;

@Builder(setterPrefix = "with")
public record RoutePointEntity(
        String trainId,
        RoutePointType routePointType,
        int position,
        String tiploc,
        String activityType,
        String plannedActivityType,
        boolean isCancelled,
        String platform,
        LocalTime plannedTimeOfArrival,
        LocalTime plannedTimeOfDeparture,
        LocalTime workingTimeOfArrival,
        LocalTime workingTimeOfDeparture,
        String falseDestination,
        int delayMinutes,
        LocalTime workingTimeOfPassing) {
}
