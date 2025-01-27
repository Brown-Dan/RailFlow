package me.danbrown.railflow.service.model;

import java.time.LocalTime;

public record Destination(String tiploc, LocalTime scheduledArrivalTime) {
}
