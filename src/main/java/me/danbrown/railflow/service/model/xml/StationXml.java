package me.danbrown.railflow.service.model.xml;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.Getter;

@XmlAccessorType(XmlAccessType.FIELD)
@Getter
public class StationXml {

    @XmlAttribute(name = "tpl", required = true)
    private String tpl;

    @XmlAttribute(name = "act")
    private String act;

    @XmlAttribute(name = "planAct")
    private String planAct;

    @XmlAttribute(name = "can")
    private boolean can;

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

    @XmlAttribute(name = "rdelay")
    private int rdelay;

    @XmlAttribute(name = "wtp")
    private String wtp;
}
