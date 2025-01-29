package me.danbrown.railflow.service.model.callingpoints;

import lombok.Builder;
import me.danbrown.railflow.model.Station;
import me.danbrown.railflow.repository.model.RoutePointEntity;
import me.danbrown.railflow.service.model.xml.StationXml;

import java.time.LocalTime;

import static me.danbrown.railflow.utils.MappingUtils.stringToLocalTime;

@Builder(setterPrefix = "with")
public record IntermediatePassingPoint(ScheduleAttributes scheduleAttributes,
                                       LocalTime workingScheduledTimeOfPassing,
                                       int delayMinutes) implements RoutePoint {

    public static IntermediatePassingPoint fromXml(StationXml station) {
        return IntermediatePassingPoint.builder()
                .withScheduleAttributes(ScheduleAttributes.fromXml(station))
                .withDelayMinutes(station.getRdelay())
                .withWorkingScheduledTimeOfPassing(stringToLocalTime(station.getWtp()))
                .build();
    }

    @Override
    public Station station() {
        return null;
    }

    @Override
    public RoutePointType routePointType() {
        return RoutePointType.IP;
    }

    public RoutePointEntity toEntity() {
        RoutePointEntity.RoutePointEntityBuilder builder = RoutePointEntity.builder();
        scheduleAttributes.addToEntityBuilder(builder);
        return builder.withWorkingTimeOfPassing(workingScheduledTimeOfPassing)
                .withDelayMinutes(delayMinutes)
                .withRoutePointType(RoutePointType.IP)
                .build();
    }

    public static IntermediatePassingPoint fromEntity(RoutePointEntity routePointEntity) {
        return IntermediatePassingPoint.builder()
                .withScheduleAttributes(ScheduleAttributes.fromEntity(routePointEntity))
                .withWorkingScheduledTimeOfPassing(routePointEntity.workingTimeOfPassing())
                .withDelayMinutes(routePointEntity.delayMinutes())
                .build();
    }
}


