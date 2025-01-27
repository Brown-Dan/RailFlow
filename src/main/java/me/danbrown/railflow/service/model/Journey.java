package me.danbrown.railflow.service.model;

import lombok.Builder;

import java.time.LocalDate;

@Builder
public record Journey(String trainId, LocalDate scheduledStartDate, Origin origin, Destination destination) {
}
