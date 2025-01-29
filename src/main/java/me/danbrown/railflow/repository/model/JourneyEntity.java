package me.danbrown.railflow.repository.model;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder(setterPrefix = "with")
public record JourneyEntity(String trainId,
                            LocalDate scheduledStartDate,
                            List<RoutePointEntity> routePointEntities) {
}
