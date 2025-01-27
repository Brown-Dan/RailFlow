package me.danbrown.railflow.service.model;

import java.time.LocalDateTime;
import java.time.LocalTime;

public record Origin(String tiploc, LocalTime scheduledDepartureTime) {
}
