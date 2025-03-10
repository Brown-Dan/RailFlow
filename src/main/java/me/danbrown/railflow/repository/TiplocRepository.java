package me.danbrown.railflow.repository;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import me.danbrown.railflow.model.Station;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class TiplocRepository {

    private final ObjectMapper objectMapper;
    private final Map<String, Station> tiplocToStationMap;

    public TiplocRepository(ObjectMapper objectMapper) throws IOException {
        this.objectMapper = objectMapper;
        this.tiplocToStationMap = objectMapper.readValue(
                new ClassPathResource("stations.json").getFile(),
                new TypeReference<List<Station>>() {
                }
        ).stream().collect(Collectors.toMap(Station::tiploc, station -> station));
    }

    public Station getStationByTiploc(String tiploc) {
        return tiplocToStationMap.get(tiploc);
    }
}
