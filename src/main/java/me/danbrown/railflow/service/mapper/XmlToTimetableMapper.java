package me.danbrown.railflow.service.mapper;

import me.danbrown.railflow.service.model.Destination;
import me.danbrown.railflow.service.model.Journey;
import me.danbrown.railflow.service.model.Origin;
import me.danbrown.railflow.service.model.Timetable;
import me.danbrown.railflow.service.model.xml.JourneyXml;
import me.danbrown.railflow.service.model.xml.StationXml;
import me.danbrown.railflow.service.model.xml.TimetableXml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static java.util.Collections.emptyList;

@Component
public class XmlToTimetableMapper {

    private static final Logger LOG = LoggerFactory.getLogger(XmlToTimetableMapper.class);

    public Timetable map(TimetableXml timetableXml) {
        return Timetable.builder()
                .timetableId(timetableXml.getTimetableID())
                .journeys(Optional.ofNullable(timetableXml.getJourneys()).map(this::mapJourneys).orElse(emptyList()))
                .build();
    }

    private List<Journey> mapJourneys(List<JourneyXml> journeys) {
        try {
            return journeys.stream()
                    .map(journey ->
                            Journey.builder()
                                    .trainId(journey.getRid())
                                    .scheduledStartDate(LocalDate.parse(journey.getSsd()))
                                    .origin(mapOrigin(journey.getOrigins()))
                                    .destination(mapDestination(journey.getDestinations()))
                                    .build()
                    )
                    .toList();
        } catch (RuntimeException e) {
            LOG.error("Failed to map journey with rids {}", journeys.stream().map(JourneyXml::getRid).toList(), e);
            return emptyList();
        }
    }

    private Origin mapOrigin(List<StationXml> stationsXml) {
        if (stationsXml == null || stationsXml.isEmpty()) {
            return null;
        }
        return new Origin(stationsXml.getFirst().getTpl(), LocalTime.parse(stationsXml.getFirst().getWtd()));
    }

    private Destination mapDestination(List<StationXml> stationsXml) {
        if (stationsXml == null || stationsXml.isEmpty()) {
            return null;
        }
        return new Destination(stationsXml.getFirst().getTpl(), LocalTime.parse(stationsXml.getFirst().getWta()));
    }
}
