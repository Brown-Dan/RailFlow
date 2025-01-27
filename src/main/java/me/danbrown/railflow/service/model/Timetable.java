package me.danbrown.railflow.service.model;

import java.util.List;

public record Timetable(List<Journey> journeys, String timetableId) {
}
