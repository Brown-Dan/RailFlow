package me.danbrown.railflow.service.model.callingpoints;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import me.danbrown.railflow.repository.model.RoutePointEntity;
import me.danbrown.railflow.service.model.xml.StationXml;

import java.time.LocalTime;

import static me.danbrown.railflow.utils.MappingUtils.stringToLocalTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record CallingPointAttributes(LocalTime publicScheduledTimeOfArrival, LocalTime publicScheduledTimeOfDeparture) {

    public static CallingPointAttributes fromXml(StationXml stationXml) {
        return CallingPointAttributes.builder()
                .publicScheduledTimeOfArrival(stringToLocalTime(stationXml.getPta()))
                .publicScheduledTimeOfDeparture(stringToLocalTime(stationXml.getPtd()))
                .build();
    }

    public void addToEntityBuilder(RoutePointEntity.RoutePointEntityBuilder builder) {
        builder.withPlannedTimeOfArrival(publicScheduledTimeOfArrival).withPlannedTimeOfDeparture(publicScheduledTimeOfDeparture);
    }

    public static CallingPointAttributes fromEntity(RoutePointEntity routePointEntity) {
        return CallingPointAttributes.builder()
                .publicScheduledTimeOfArrival(routePointEntity.plannedTimeOfArrival())
                .publicScheduledTimeOfDeparture(routePointEntity.plannedTimeOfDeparture())
                .build();
    }
}
