package me.danbrown.railflow.service.model;

import lombok.Builder;
import me.danbrown.railflow.service.model.callingpoints.RoutePoint;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Builder(setterPrefix = "with")
public record Journey(String trainId, LocalDate scheduledStartDate, List<RoutePoint> route) {
}
