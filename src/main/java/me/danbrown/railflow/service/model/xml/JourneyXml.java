package me.danbrown.railflow.service.model.xml;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import me.danbrown.railflow.service.model.xml.wrapper.*;

import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@Getter
public class JourneyXml {

    @XmlElements({
            @XmlElement(name = "OR", type = PassengerOriginWrapper.class, namespace = "http://www.thalesgroup.com/rtti/XmlTimetable/v8"),
            @XmlElement(name = "OPOR", type = OperationalOriginWrapper.class, namespace = "http://www.thalesgroup.com/rtti/XmlTimetable/v8"),
            @XmlElement(name = "IP", type = PassengerIntermediateWrapper.class, namespace = "http://www.thalesgroup.com/rtti/XmlTimetable/v8"),
            @XmlElement(name = "OPIP", type = OperationalIntermediateWrapper.class, namespace = "http://www.thalesgroup.com/rtti/XmlTimetable/v8"),
            @XmlElement(name = "PP", type = PassingPointWrapper.class, namespace = "http://www.thalesgroup.com/rtti/XmlTimetable/v8"),
            @XmlElement(name = "DT", type = PassengerDestinationWrapper.class, namespace = "http://www.thalesgroup.com/rtti/XmlTimetable/v8"),
            @XmlElement(name = "OPDT", type = OperationalDestinationWrapper.class, namespace = "http://www.thalesgroup.com/rtti/XmlTimetable/v8")
    })
    private List<StationXml> stations;

    @XmlElement(name = "cancelReason", namespace = "http://www.thalesgroup.com/rtti/XmlTimetable/v8")
    private String cancelReason;

    @XmlAttribute(name = "rid", required = true)
    private String rid;

    @XmlAttribute(name = "uid", required = true)
    private String uid;

    @XmlAttribute(name = "trainId", required = true)
    private String trainId;

    @XmlAttribute(name = "ssd", required = true)
    private String ssd;

    @XmlAttribute(name = "toc", required = true)
    private String toc;

    @XmlAttribute(name = "status")
    private String status;

    @XmlAttribute(name = "trainCat")
    private String trainCat;

    @XmlAttribute(name = "isPassengerSvc")
    private Boolean isPassengerSvc;

    @XmlAttribute(name = "deleted")
    private Boolean deleted;

    @XmlAttribute(name = "isCharter")
    private Boolean isCharter;

    @XmlAttribute(name = "qtrain")
    private Boolean qtrain;

    @XmlAttribute(name = "can")
    private Boolean can;
}
