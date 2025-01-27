package me.danbrown.railflow.service.model.xml;

import jakarta.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class StationXml {

    @XmlAttribute(name = "tpl", required = true)
    private String tpl;

    @XmlAttribute(name = "act")
    private String act;

    @XmlAttribute(name = "planAct")
    private String planAct;

    @XmlAttribute(name = "can")
    private Boolean can;

    @XmlAttribute(name = "plat")
    private String plat;

    @XmlAttribute(name = "pta")
    private String pta;

    @XmlAttribute(name = "ptd")
    private String ptd;

    @XmlAttribute(name = "wta")
    private String wta;

    @XmlAttribute(name = "wtd", required = true)
    private String wtd;

    @XmlAttribute(name = "fd")
    private String fd;

    public String getTpl() {
        return tpl;
    }

    public String getAct() {
        return act;
    }

    public String getPlanAct() {
        return planAct;
    }

    public Boolean getCan() {
        return can;
    }

    public String getPlat() {
        return plat;
    }

    public String getPta() {
        return pta;
    }

    public String getPtd() {
        return ptd;
    }

    public String getWta() {
        return wta;
    }

    public String getWtd() {
        return wtd;
    }

    public String getFd() {
        return fd;
    }
}
