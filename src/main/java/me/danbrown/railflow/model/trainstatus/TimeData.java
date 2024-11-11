package me.danbrown.railflow.model.trainstatus;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.time.LocalTime;
import java.util.Optional;

import static me.danbrown.railflow.utils.XmlUtils.nodeToLocalTime;

/**
 * Type describing time-based forecast attributes for a TS arrival/departure/pass
 */
public record TimeData(LocalTime estimatedTime,
                       LocalTime workingEstimatedTime,
                       LocalTime actualTime,
                       boolean actualTimeRemoved,
                       String actualTimeClass,
                       LocalTime estimatedTimeMin,
                       boolean estimatedTimeUnknown,
                       boolean delayed,
                       String source,
                       String sourceType) {

    public static TimeData fromNode(Node node) {
        NamedNodeMap attributes = node.getAttributes();
        return Builder.aTimeData()
                .withEstimatedTime(nodeToLocalTime(attributes.getNamedItem("et")))
                .withWorkingEstimatedTime(nodeToLocalTime(attributes.getNamedItem("wet")))
                .withActualTime(nodeToLocalTime(attributes.getNamedItem("at")))
                .withActualTimeRemoved(attributes.getNamedItem("atRemoved") != null)
                .withActualTimeClass(Optional.ofNullable(attributes.getNamedItem("atClass")).map(Node::getNodeValue).orElse(null))
                .withEstimatedTimeMin(nodeToLocalTime(attributes.getNamedItem("etmin")))
                .withEstimatedTimeUnknown(attributes.getNamedItem("etUnknown") != null)
                .withDelayed(attributes.getNamedItem("delayed") != null)
                .withSource(Optional.ofNullable(attributes.getNamedItem("src")).map(Node::getNodeValue).orElse(null))
                .withSourceType(Optional.ofNullable(attributes.getNamedItem("srcType")).map(Node::getNodeValue).orElse(null))
                .build();
    }

    public static final class Builder {
        private LocalTime estimatedTime;
        private LocalTime workingEstimatedTime;
        private LocalTime actualTime;
        private boolean actualTimeRemoved;
        private String actualTimeClass;
        private LocalTime estimatedTimeMin;
        private boolean estimatedTimeUnknown;
        private boolean delayed;
        private String source;
        private String sourceType;

        private Builder() {
        }

        public static Builder aTimeData() {
            return new Builder();
        }

        public Builder withEstimatedTime(LocalTime estimatedTime) {
            this.estimatedTime = estimatedTime;
            return this;
        }

        public Builder withWorkingEstimatedTime(LocalTime workingEstimatedTime) {
            this.workingEstimatedTime = workingEstimatedTime;
            return this;
        }

        public Builder withActualTime(LocalTime actualTime) {
            this.actualTime = actualTime;
            return this;
        }

        public Builder withActualTimeRemoved(boolean actualTimeRemoved) {
            this.actualTimeRemoved = actualTimeRemoved;
            return this;
        }

        public Builder withActualTimeClass(String actualTimeClass) {
            this.actualTimeClass = actualTimeClass;
            return this;
        }

        public Builder withEstimatedTimeMin(LocalTime estimatedTimeMin) {
            this.estimatedTimeMin = estimatedTimeMin;
            return this;
        }

        public Builder withEstimatedTimeUnknown(boolean estimatedTimeUnknown) {
            this.estimatedTimeUnknown = estimatedTimeUnknown;
            return this;
        }

        public Builder withDelayed(boolean delayed) {
            this.delayed = delayed;
            return this;
        }

        public Builder withSource(String source) {
            this.source = source;
            return this;
        }

        public Builder withSourceType(String sourceType) {
            this.sourceType = sourceType;
            return this;
        }

        public TimeData build() {
            return new TimeData(estimatedTime, workingEstimatedTime, actualTime, actualTimeRemoved, actualTimeClass, estimatedTimeMin, estimatedTimeUnknown, delayed, source, sourceType);
        }
    }
}
