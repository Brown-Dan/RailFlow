package me.danbrown.railflow.service.model;

import lombok.Builder;

import java.time.LocalTime;

@Builder
public record Destination(String tiploc, LocalTime scheduledArrivalTime, String platform) {
}
