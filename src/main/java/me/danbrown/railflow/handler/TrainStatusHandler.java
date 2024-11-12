package me.danbrown.railflow.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.danbrown.railflow.model.trainstatus.TrainStatus;
import me.danbrown.railflow.service.SmsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Component
public class TrainStatusHandler {

    private static final Logger LOG = LoggerFactory.getLogger(TrainStatusHandler.class);

    private final SmsService smsService;
    private final ObjectMapper objectMapper;

    public TrainStatusHandler(SmsService smsService, ObjectMapper objectMapper) {
        this.smsService = smsService;
        this.objectMapper = objectMapper;
    }
    public void handleTrainStatus(NodeList nodes) throws JsonProcessingException {
        if (nodes.getLength() > 0) {
            Node trainStatusNode = nodes.item(0);
            TrainStatus trainStatus = TrainStatus.fromNode(trainStatusNode);
            if (trainStatus.locations().stream().anyMatch(location -> location.tiploc().equals("GLSP"))) {
                if (trainStatus.disruptionReason() != null) {
                    smsService.sendSms("Delayed or Cancelled Train to Glossop with details: %s".formatted(objectMapper.writeValueAsString(trainStatus)));
                }
            }
        }
    }
}
