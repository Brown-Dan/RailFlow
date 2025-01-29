package me.danbrown.railflow.service.mapper;

import me.danbrown.railflow.service.model.Journey;
import me.danbrown.railflow.service.model.Timetable;
import me.danbrown.railflow.service.model.callingpoints.*;
import me.danbrown.railflow.service.model.xml.JourneyXml;
import me.danbrown.railflow.service.model.xml.StationXml;
import me.danbrown.railflow.service.model.xml.TimetableXml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Collections.emptyList;
import static me.danbrown.railflow.utils.MappingUtils.stringToLocalDate;

@Component
public class XmlToTimetableMapper {

    private static final Logger LOG = LoggerFactory.getLogger(XmlToTimetableMapper.class);

    public Timetable map(TimetableXml timetable) {
        return Timetable.builder()
                .timetableId(timetable.getTimetableId())
                .journeys(Optional.ofNullable(timetable.getJourneys()).orElse(emptyList()).stream().map(this::mapJourney).filter(Objects::nonNull).toList())
                .build();
    }

    private Journey mapJourney(JourneyXml journey) {
        return Journey.builder()
                .withTrainId(journey.getRid())
                .withScheduledStartDate(stringToLocalDate(journey.getSsd()))
                .withRoute(mapRoutePoints(journey.getStations()))
                .build();
    }

    private List<RoutePoint> mapRoutePoints(List<StationXml> station) {
        if (station == null) {
            return emptyList();
        }
        return station.stream().map(this::mapRoutePoint).toList();
    }

    private RoutePoint mapRoutePoint(StationXml station) {
        RoutePointType routePointType = RoutePointType.fromClass(station);

        return switch (routePointType) {
            case OR -> PassengerOriginPoint.fromXml(station);
            case OPOR -> OperationalOriginPoint.fromXml(station);
            case IP -> PassengerIntermediatePoint.fromXml(station);
            case OPIP -> OperationalIntermediatePoint.fromXml(station);
            case PP -> IntermediatePassingPoint.fromXml(station);
            case DT -> PassengerDestinationPoint.fromXml(station);
            case OPDT -> OperationalDestinationPoint.fromXml(station);
        };
    }
}
