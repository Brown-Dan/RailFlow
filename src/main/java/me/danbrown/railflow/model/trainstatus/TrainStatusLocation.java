package me.danbrown.railflow.model.trainstatus;

import me.danbrown.railflow.model.types.CircularTimes;
import me.danbrown.railflow.model.types.PlatformData;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

import static me.danbrown.railflow.utils.XmlUtils.findChildNode;


/**
 * Forecast data for an individual location in the service's schedule
 */
public record TrainStatusLocation(TimeData arrival,
                                  TimeData departure,
                                  TimeData pass,
                                  PlatformData platformData,
                                  boolean suppressed,
                                  Integer length,
                                  boolean detachFront,
                                  String tiploc,
                                  CircularTimes circularTimes
) {
    public static List<TrainStatusLocation> fromNode(Node node) {
        List<TrainStatusLocation> locations = new ArrayList<>();
        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            if (nodeList.item(i).getNodeName().equals("ns5:Location")) {
                locations.add(toLocation(nodeList.item(i)));
            }
        }
        return locations;
    }

    private static TrainStatusLocation toLocation(Node node) {
        NamedNodeMap attributes = node.getAttributes();
        return Builder.aTrainStatusLocation()
                .withTiploc(attributes.getNamedItem("tpl").getTextContent())
                .withCircularTimes(CircularTimes.fromNamedNodeMap(attributes))
                .withArrival(findChildNode(node, "ns5:arr").map(TimeData::fromNode).orElse(null))
                .withPass(findChildNode(node, "ns5:pass").map(TimeData::fromNode).orElse(null))
                .withDeparture(findChildNode(node, "ns5:dep").map(TimeData::fromNode).orElse(null))
                .withPlatformData(findChildNode(node, "ns5:plat").map(PlatformData::fromNode).orElse(null))
                .withSuppressed(findChildNode(node, "ns5:suppr").isPresent())
                .withLength(findChildNode(node, "ns5:length").map(Node::getTextContent).map(Integer::parseInt).orElse(null))
                .withDetachFront(findChildNode(node, "ns5:detachFront").isPresent())
                .build();
    }

    public static final class Builder {
        private TimeData arrival;
        private TimeData departure;
        private TimeData pass;
        private PlatformData platformData;
        private boolean suppressed;
        private Integer length;
        private boolean detachFront;
        private String tiploc;
        private CircularTimes circularTimes;

        private Builder() {
        }

        public static Builder aTrainStatusLocation() {
            return new Builder();
        }

        public Builder withArrival(TimeData arrival) {
            this.arrival = arrival;
            return this;
        }

        public Builder withDeparture(TimeData departure) {
            this.departure = departure;
            return this;
        }

        public Builder withPass(TimeData pass) {
            this.pass = pass;
            return this;
        }

        public Builder withPlatformData(PlatformData platformData) {
            this.platformData = platformData;
            return this;
        }

        public Builder withSuppressed(boolean suppressed) {
            this.suppressed = suppressed;
            return this;
        }

        public Builder withLength(Integer length) {
            this.length = length;
            return this;
        }

        public Builder withDetachFront(boolean detachFront) {
            this.detachFront = detachFront;
            return this;
        }

        public Builder withTiploc(String tiploc) {
            this.tiploc = tiploc;
            return this;
        }

        public Builder withCircularTimes(CircularTimes circularTimes) {
            this.circularTimes = circularTimes;
            return this;
        }

        public TrainStatusLocation build() {
            return new TrainStatusLocation(arrival, departure, pass, platformData, suppressed, length, detachFront, tiploc, circularTimes);
        }
    }
}
