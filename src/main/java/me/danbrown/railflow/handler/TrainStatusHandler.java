package me.danbrown.railflow.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.danbrown.railflow.model.trainstatus.TrainStatus;
import me.danbrown.railflow.repository.JourneyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Component
public class TrainStatusHandler {

    private static final Logger LOG = LoggerFactory.getLogger(TrainStatusHandler.class);

    private final ObjectMapper objectMapper;
    private final JourneyRepository journeyRepository;

    public TrainStatusHandler(ObjectMapper objectMapper, JourneyRepository journeyRepository) {
        this.objectMapper = objectMapper;
        this.journeyRepository = journeyRepository;
    }

    public void handleTrainStatus(NodeList nodes) {
        if (nodes.getLength() > 0) {
            Node trainStatusNode = nodes.item(0);
            TrainStatus trainStatus = TrainStatus.fromNode(trainStatusNode);
            LOG.info("Handling train status: {}", trainStatus.rttiTrainId());
        }
    }
}
