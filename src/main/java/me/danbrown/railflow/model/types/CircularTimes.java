package me.danbrown.railflow.model.types;

import org.w3c.dom.NamedNodeMap;

import java.time.LocalTime;
import java.util.Optional;

/**
 * A scheduled time used to distinguish a location on circular routes.
 * Note that all scheduled time attributes are marked as optional, but at least one must always be supplied.
 * Only one value is required, and typically this should be the wtd value.
 * However, for locations that have no wtd, or for clients that deal exclusively with public times, another value that is valid for the location may be supplied.
 */
public record CircularTimes(LocalTime workingTimeOfArrival,
                            LocalTime workingTimeOfDeparture,
                            LocalTime workingTimeOfPass,
                            LocalTime publicTimeOfArrival,
                            LocalTime publicTimeOfDeparture) {


    public static CircularTimes fromNamedNodeMap(NamedNodeMap attributes) {
        return Builder.aCircularTimes()
                .withWorkingTimeOfArrival(Optional.ofNullable(attributes.getNamedItem("wta")).map(text -> LocalTime.parse(text.getTextContent())).orElse(null))
                .withWorkingTimeOfDeparture(Optional.ofNullable(attributes.getNamedItem("wtd")).map(text -> LocalTime.parse(text.getTextContent())).orElse(null))
                .withWorkingTimeOfPass(Optional.ofNullable(attributes.getNamedItem("wtp")).map(text -> LocalTime.parse(text.getTextContent())).orElse(null))
                .withPublicTimeOfArrival(Optional.ofNullable(attributes.getNamedItem("pta")).map(text -> LocalTime.parse(text.getTextContent())).orElse(null))
                .withPublicTimeOfDeparture(Optional.ofNullable(attributes.getNamedItem("ptd")).map(text -> LocalTime.parse(text.getTextContent())).orElse(null))
                .build();
    }

    public static final class Builder {
        private LocalTime workingTimeOfArrival;
        private LocalTime workingTimeOfDeparture;
        private LocalTime workingTimeOfPass;
        private LocalTime publicTimeOfArrival;
        private LocalTime publicTimeOfDeparture;

        private Builder() {
        }

        public static Builder aCircularTimes() {
            return new Builder();
        }

        public Builder withWorkingTimeOfArrival(LocalTime workingTimeOfArrival) {
            this.workingTimeOfArrival = workingTimeOfArrival;
            return this;
        }

        public Builder withWorkingTimeOfDeparture(LocalTime workingTimeOfDeparture) {
            this.workingTimeOfDeparture = workingTimeOfDeparture;
            return this;
        }

        public Builder withWorkingTimeOfPass(LocalTime workingTimeOfPass) {
            this.workingTimeOfPass = workingTimeOfPass;
            return this;
        }

        public Builder withPublicTimeOfArrival(LocalTime publicTimeOfArrival) {
            this.publicTimeOfArrival = publicTimeOfArrival;
            return this;
        }

        public Builder withPublicTimeOfDeparture(LocalTime publicTimeOfDeparture) {
            this.publicTimeOfDeparture = publicTimeOfDeparture;
            return this;
        }

        public CircularTimes build() {
            return new CircularTimes(workingTimeOfArrival, workingTimeOfDeparture, workingTimeOfPass, publicTimeOfArrival, publicTimeOfDeparture);
        }
    }
}
