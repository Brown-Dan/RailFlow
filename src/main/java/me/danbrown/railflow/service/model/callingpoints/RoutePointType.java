package me.danbrown.railflow.service.model.callingpoints;

import me.danbrown.railflow.service.model.xml.StationXml;
import me.danbrown.railflow.service.model.xml.wrapper.*;

public enum RoutePointType {
    OR,
    OPOR,
    IP,
    OPIP,
    PP,
    DT,
    OPDT;

    public static RoutePointType fromClass(StationXml station) {
        if (station instanceof OperationalDestinationWrapper) {
            return OPDT;
        }
        if (station instanceof OperationalIntermediateWrapper) {
            return OPIP;
        }
        if (station instanceof OperationalOriginWrapper) {
            return OPOR;
        }
        if (station instanceof PassengerDestinationWrapper) {
            return DT;
        }
        if (station instanceof PassengerIntermediateWrapper) {
            return IP;
        }
        if (station instanceof PassengerOriginWrapper) {
            return OR;
        }
        if (station instanceof PassingPointWrapper) {
            return PP;
        }
        throw new IllegalArgumentException("Unknown station type: " + station.getClass().getName());
    }
}
