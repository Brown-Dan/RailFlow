package me.danbrown.openrailapi.model.types;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

import java.util.Optional;


/**
 * Platform number with associated flags
 */
public record PlatformData(
        String platformNumber,
        boolean platsup,
        boolean cisPlatsup,
        PlatformSource platformSource,
        boolean confirmed) {

    public static PlatformData fromNode(Node node) {
        NamedNodeMap attributes = node.getAttributes();
        return Builder.aPlatformData()
                .withPlatformNumber(Optional.ofNullable(node.getTextContent()).orElse(null))
                .withPlatformSource(Optional.ofNullable(attributes.getNamedItem("platsrc")).map(Node::getNodeValue).map(PlatformSource::fromInitial).orElse(null))
                .withCisPlatsup(attributes.getNamedItem("cisPlatsup") != null)
                .withPlatsup(attributes.getNamedItem("platsup") != null)
                .withConfirmed(attributes.getNamedItem("conf") != null)
                .build();
    }

    public static final class Builder {
        private String platformNumber;
        private boolean platsup;
        private boolean cisPlatsup;
        private PlatformSource platformSource;
        private boolean confirmed;

        private Builder() {
        }

        public static Builder aPlatformData() {
            return new Builder();
        }

        public Builder withPlatformNumber(String platformNumber) {
            this.platformNumber = platformNumber;
            return this;
        }

        public Builder withPlatsup(boolean platsup) {
            this.platsup = platsup;
            return this;
        }

        public Builder withCisPlatsup(boolean cisPlatsup) {
            this.cisPlatsup = cisPlatsup;
            return this;
        }

        public Builder withPlatformSource(PlatformSource platformSource) {
            this.platformSource = platformSource;
            return this;
        }

        public Builder withConfirmed(boolean confirmed) {
            this.confirmed = confirmed;
            return this;
        }

        public PlatformData build() {
            return new PlatformData(platformNumber, platsup, cisPlatsup, platformSource, confirmed);
        }
    }
}
