package me.danbrown.railflow.service.model;

import lombok.Builder;

import java.time.LocalTime;

@Builder
public record Origin(String tiploc, LocalTime scheduledDepartureTime, String platform) {
}
