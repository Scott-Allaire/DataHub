package org.coder229.datahub.service.messages;

import com.fasterxml.jackson.databind.JsonNode;
import org.coder229.datahub.model.Reading;
import org.coder229.datahub.model.Source;
import org.coder229.datahub.repository.ReadingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.stream.Stream;

@Component
public class WeatherMessageProcessor implements MessageProcessor {

    @Autowired
    ReadingRepository readingRepository;

    @Override
    public boolean canProcess(String sourceName) {
        return sourceName.startsWith("topic/weather");
    }

    @Override
    public Stream<Reading> process(Source source, JsonNode jsonNode) {
        Instant instant = Instant.ofEpochSecond(jsonNode.findValue("epoch").asLong());
        ZonedDateTime dateTime = ZonedDateTime.ofInstant(instant, ZoneId.of("UTC"))
                .withZoneSameLocal(ZoneId.systemDefault());

        Reading humidityReading = new Reading();
        humidityReading.category = "Environment";
        humidityReading.code = "Humidity";
        humidityReading.value = Double.toString(jsonNode.findValue("hum").doubleValue());
        humidityReading.units = "%";
        humidityReading.source = source;
        humidityReading.effective = dateTime;

        Reading temperatureReading = new Reading();
        temperatureReading.category = "Environment";
        temperatureReading.code = "Temperature";
        temperatureReading.value = Double.toString(jsonNode.findValue("tempf").doubleValue());
        temperatureReading.units = "F";
        temperatureReading.source = source;
        temperatureReading.effective = dateTime;

        Reading heatIndexReading = new Reading();
        heatIndexReading.category = "Environment";
        heatIndexReading.code = "HeatIndex";
        heatIndexReading.value = Double.toString(jsonNode.findValue("heat_ind").doubleValue());
        heatIndexReading.units = "F";
        heatIndexReading.source = source;
        heatIndexReading.effective = dateTime;

        return Stream.of(
                readingRepository.save(humidityReading),
                readingRepository.save(temperatureReading),
                readingRepository.save(heatIndexReading)
        );
    }
}
