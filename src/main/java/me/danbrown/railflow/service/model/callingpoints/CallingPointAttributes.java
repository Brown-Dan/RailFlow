package me.danbrown.railflow.service.model.callingpoints;

import lombok.Builder;
import me.danbrown.railflow.repository.model.RoutePointEntity;
import me.danbrown.railflow.service.model.xml.StationXml;

import java.time.LocalTime;

import static me.danbrown.railflow.utils.MappingUtils.stringToLocalTime;

@Builder
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
}
