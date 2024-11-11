package me.danbrown.railflow.model.trainstatus;

import me.danbrown.railflow.model.types.DisruptionReason;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.time.LocalDate;
import java.util.List;

import static me.danbrown.railflow.utils.XmlUtils.*;

/**
 * Train Status. Update to the "real time" forecast data for a service.
 */
public record TrainStatus(List<TrainStatusLocation> locations,
                          String rttiTrainId,
                          String trainUid,
                          LocalDate scheduledStartDate,
                          boolean isReverseFormation,
                          DisruptionReason disruptionReason) {

    public static TrainStatus fromNode(Node node) {
        NamedNodeMap attributes = node.getAttributes();
        return Builder.aTrainStatus()
                .withRttiTrainId(nodeToString(attributes.getNamedItem("rid")))
                .withTrainUid(nodeToString(attributes.getNamedItem("uid")))
                .withScheduledStartDate(nodeToLocalDate(attributes.getNamedItem("ssd")))
                .withIsReverseFormation(attributes.getNamedItem("isReverseFormation") != null)
                .withLocations(TrainStatusLocation.fromNode(node))
                .withDisruptionReason(findChildNode(node, "ns5:LateReason").map(DisruptionReason::fromNode).orElse(null))
                .build();
    }

    public static final class Builder {
        private List<TrainStatusLocation> locations;
        private String rttiTrainId;
        private String trainUid;
        private LocalDate scheduledStartDate;
        private boolean isReverseFormation;
        private DisruptionReason disruptionReason;

        private Builder() {
        }

        public static Builder aTrainStatus() {
            return new Builder();
        }

        public Builder withLocations(List<TrainStatusLocation> locations) {
            this.locations = locations;
            return this;
        }

        public Builder withRttiTrainId(String rttiTrainId) {
            this.rttiTrainId = rttiTrainId;
            return this;
        }

        public Builder withTrainUid(String trainUid) {
            this.trainUid = trainUid;
            return this;
        }

        public Builder withScheduledStartDate(LocalDate scheduledStartDate) {
            this.scheduledStartDate = scheduledStartDate;
            return this;
        }

        public Builder withIsReverseFormation(boolean isReverseFormation) {
            this.isReverseFormation = isReverseFormation;
            return this;
        }

        public Builder withDisruptionReason(DisruptionReason disruptionReason) {
            this.disruptionReason = disruptionReason;
            return this;
        }

        public TrainStatus build() {
            return new TrainStatus(locations, rttiTrainId, trainUid, scheduledStartDate, isReverseFormation, disruptionReason);
        }
    }
}
