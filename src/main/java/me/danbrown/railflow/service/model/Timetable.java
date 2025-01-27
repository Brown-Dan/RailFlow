package me.danbrown.railflow.service.model;

import lombok.Builder;

import java.util.List;

@Builder
public record Timetable(List<Journey> journeys, String timetableId) {
}
