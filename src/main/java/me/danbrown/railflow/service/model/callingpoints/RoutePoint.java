package me.danbrown.railflow.service.model.callingpoints;

import me.danbrown.railflow.repository.model.JourneyEntity;
import me.danbrown.railflow.repository.model.RoutePointEntity;

public interface RoutePoint {

    ScheduleAttributes scheduleAttributes();

    RoutePointEntity toEntity();
}
