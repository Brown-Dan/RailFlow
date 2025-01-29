package me.danbrown.railflow.utils;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

public class MappingUtils {

    public static LocalTime stringToLocalTime(String localTimeAsString) {
        return Optional.ofNullable(localTimeAsString).map(LocalTime::parse).orElse(null);
    }

    public static LocalDate stringToLocalDate(String localDateAsString) {
        return Optional.ofNullable(localDateAsString).map(LocalDate::parse).orElse(null);
    }
}
