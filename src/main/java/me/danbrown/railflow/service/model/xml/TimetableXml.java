package me.danbrown.railflow.service.model.xml;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;

import java.util.List;

@XmlRootElement(name = "PportTimetable", namespace = "http://www.thalesgroup.com/rtti/XmlTimetable/v8")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
public class TimetableXml {

    @XmlElement(name = "Journey", namespace = "http://www.thalesgroup.com/rtti/XmlTimetable/v8")
    private List<JourneyXml> journeys;

    @XmlAttribute(name = "timetableID", required = true)
    private String timetableId;
}
