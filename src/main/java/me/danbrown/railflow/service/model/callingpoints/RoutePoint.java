package me.danbrown.railflow.service.model.callingpoints;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import me.danbrown.railflow.repository.model.RoutePointEntity;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface RoutePoint {

    ScheduleAttributes scheduleAttributes();

    @JsonGetter("routePointType")
    RoutePointType routePointType();

    RoutePointEntity toEntity();
}
