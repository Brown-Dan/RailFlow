package me.danbrown.railflow.service.model;

import me.danbrown.railflow.service.DarwinFileService;
import me.danbrown.railflow.service.model.xml.JourneyXml;
import me.danbrown.railflow.service.model.xml.StationXml;
import me.danbrown.railflow.service.model.xml.TimetableXml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class TimetableXmlMapper {

    private static final Logger LOG = LoggerFactory.getLogger(TimetableXmlMapper.class);

    public Timetable map(TimetableXml timetableXml) {
        try {
            return new Timetable(timetableXml.getJourneys().stream().map(this::mapJourney).toList(), timetableXml.getTimetableID());
        } catch (RuntimeException e) {
            LOG.error("Failed to map timetable with id {}", timetableXml.getTimetableID());
            return null;
        }
    }

    private Journey mapJourney(JourneyXml journeyXml) {
        try {
            return new Journey(journeyXml.getRid(), LocalDate.parse(journeyXml.getSsd()), mapOrigin(journeyXml.getOrigins().getFirst()), mapDestination(journeyXml.getDestinations().getLast()));
        } catch (RuntimeException e) {
            LOG.error("Failed to map journey with rid {}", journeyXml.getRid());
            return null;
        }
    }

    private Origin mapOrigin(StationXml stationXml) {
        return new Origin(stationXml.getTpl(), LocalTime.parse(stationXml.getWtd()));
    }

    private Destination mapDestination(StationXml stationXml) {
        return new Destination(stationXml.getTpl(), LocalTime.parse(stationXml.getWta()));
    }
}
