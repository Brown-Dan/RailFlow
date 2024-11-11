package me.danbrown.railflow.model.types;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.Optional;

import static me.danbrown.railflow.model.types.ReasonCode.getReasonFromCode;

/**
 * Type used to represent a cancellation or late running reason
 */
public record DisruptionReason(ReasonCode reasonCode, String tiploc, boolean near) {

    public static DisruptionReason fromNode(Node node) {
        NamedNodeMap attributes = node.getAttributes();
        return Builder.aDisruptionReason()
                .withNear(attributes.getNamedItem("near") != null)
                .withTiploc(Optional.ofNullable(attributes.getNamedItem("tpl")).map(Node::getTextContent).orElse(null))
                .withReasonCode(new ReasonCode(Integer.parseInt(node.getTextContent()), getReasonFromCode(Integer.parseInt(node.getTextContent()))))
                .build();
    }

    public static final class Builder {
        private ReasonCode reasonCode;
        private String tiploc;
        private boolean near;

        private Builder() {
        }

        public static Builder aDisruptionReason() {
            return new Builder();
        }

        public Builder withReasonCode(ReasonCode reasonCode) {
            this.reasonCode = reasonCode;
            return this;
        }

        public Builder withTiploc(String tiploc) {
            this.tiploc = tiploc;
            return this;
        }

        public Builder withNear(boolean near) {
            this.near = near;
            return this;
        }

        public DisruptionReason build() {
            return new DisruptionReason(reasonCode, tiploc, near);
        }
    }
}
