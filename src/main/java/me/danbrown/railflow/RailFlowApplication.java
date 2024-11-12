package me.danbrown.railflow;

import me.danbrown.railflow.service.SmsService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class RailFlowApplication {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("h:mma");

    public static void main(String[] args) {
        SpringApplication.run(RailFlowApplication.class, args);
        new Thread(() -> {
            try {
                LocalDateTime startTime = LocalDateTime.now();
                while(true) {
                    Thread.sleep(Duration.ofMinutes(90).toMillis());
                    new SmsService().sendSms("Service started running on %s at %s and has been running for %s minutes".formatted(formatDay(startTime.getDayOfWeek()), startTime.format(formatter), Duration.between(startTime, LocalDateTime.now()).toMinutes()));
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public static String formatDay(DayOfWeek day) {
        String name = day.name().toLowerCase(Locale.ROOT);
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
