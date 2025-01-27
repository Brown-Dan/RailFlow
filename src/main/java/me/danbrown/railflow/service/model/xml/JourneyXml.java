package me.danbrown.railflow.service.model.xml;

import jakarta.xml.bind.annotation.*;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class JourneyXml {

    @XmlElement(name = "OR", namespace = "http://www.thalesgroup.com/rtti/XmlTimetable/v8")
    private List<StationXml> origins;

    @XmlElement(name = "OPOR", namespace = "http://www.thalesgroup.com/rtti/XmlTimetable/v8")
    private List<StationXml> operationalOrigins;

    @XmlElement(name = "IP", namespace = "http://www.thalesgroup.com/rtti/XmlTimetable/v8")
    private List<StationXml> intermediatePoints;

    @XmlElement(name = "OPIP", namespace = "http://www.thalesgroup.com/rtti/XmlTimetable/v8")
    private List<StationXml> operationalIntermediatePoints;

    @XmlElement(name = "PP", namespace = "http://www.thalesgroup.com/rtti/XmlTimetable/v8")
    private List<StationXml> passingPoints;

    @XmlElement(name = "DT", namespace = "http://www.thalesgroup.com/rtti/XmlTimetable/v8")
    private List<StationXml> destinations;

    @XmlElement(name = "OPDT", namespace = "http://www.thalesgroup.com/rtti/XmlTimetable/v8")
    private List<StationXml> operationalDestinations;

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

    public List<StationXml> getOrigins() {
        return origins;
    }

    public List<StationXml> getOperationalOrigins() {
        return operationalOrigins;
    }

    public List<StationXml> getIntermediatePoints() {
        return intermediatePoints;
    }

    public List<StationXml> getOperationalIntermediatePoints() {
        return operationalIntermediatePoints;
    }

    public List<StationXml> getPassingPoints() {
        return passingPoints;
    }

    public List<StationXml> getDestinations() {
        return destinations;
    }

    public List<StationXml> getOperationalDestinations() {
        return operationalDestinations;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public String getRid() {
        return rid;
    }

    public String getUid() {
        return uid;
    }

    public String getTrainId() {
        return trainId;
    }

    public String getSsd() {
        return ssd;
    }

    public String getToc() {
        return toc;
    }

    public String getStatus() {
        return status;
    }

    public String getTrainCat() {
        return trainCat;
    }

    public Boolean getPassengerSvc() {
        return isPassengerSvc;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public Boolean getCharter() {
        return isCharter;
    }

    public Boolean getQtrain() {
        return qtrain;
    }

    public Boolean getCan() {
        return can;
    }
}
