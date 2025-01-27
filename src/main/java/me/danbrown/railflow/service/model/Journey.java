package me.danbrown.railflow.service.model;

import java.time.LocalDate;

public record Journey(String trainId, LocalDate scheduledStartDate, Origin origin, Destination destination) {
}
