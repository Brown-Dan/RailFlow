package me.danbrown.openrailapi.mapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.danbrown.openrailapi.model.trainstatus.TrainStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Component
public class TrainStatusHandler {

    private static final Logger LOG = LoggerFactory.getLogger(TrainStatusHandler.class);

    private final ObjectMapper objectMapper;

    public TrainStatusHandler(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public void handleTrainStatus(NodeList nodes) throws JsonProcessingException {
        if (nodes.getLength() > 0) {
            Node trainStatusNode = nodes.item(0);
            TrainStatus trainStatus = TrainStatus.fromNode(trainStatusNode);
            if (trainStatus.locations().stream().anyMatch(location -> location.tiploc().equals("GLSP"))) {
                LOG.info(objectMapper.writeValueAsString(trainStatus));
            }
        }
    }
}
