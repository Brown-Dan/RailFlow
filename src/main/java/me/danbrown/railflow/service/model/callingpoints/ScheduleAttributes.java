package me.danbrown.railflow.service.model.callingpoints;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import me.danbrown.railflow.repository.model.RoutePointEntity;
import me.danbrown.railflow.service.model.xml.StationXml;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder(setterPrefix = "with")
public record ScheduleAttributes(String tiploc, String activityType, String plannedActivityType, boolean isCancelled, String platform) {

    public static ScheduleAttributes fromXml(StationXml stationXml) {
        return ScheduleAttributes.builder()
                .withTiploc(stationXml.getTpl())
                .withActivityType(stationXml.getAct())
                .withPlannedActivityType(stationXml.getPlanAct())
                .withIsCancelled(stationXml.isCan())
                .withPlatform(stationXml.getPlat())
                .build();
    }

    public void addToEntityBuilder(RoutePointEntity.RoutePointEntityBuilder builder) {
        builder.withTiploc(tiploc).withActivityType(activityType).withPlannedActivityType(plannedActivityType).withIsCancelled(isCancelled).withPlatform(platform);
    }

    public static ScheduleAttributes fromEntity(RoutePointEntity routePointEntity) {
        return ScheduleAttributes.builder()
                .withTiploc(routePointEntity.tiploc())
                .withActivityType(routePointEntity.activityType())
                .withPlannedActivityType(routePointEntity.plannedActivityType())
                .withIsCancelled(routePointEntity.isCancelled())
                .withPlatform(routePointEntity.platform())
                .build();
    }
}
