package me.danbrown.railflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class RailFlowApplication {

    public static void main(String[] args) {
        SpringApplication.run(RailFlowApplication.class, args);
    }

}
