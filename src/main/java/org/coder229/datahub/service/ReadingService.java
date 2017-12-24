package org.coder229.datahub.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.coder229.datahub.model.Reading;
import org.coder229.datahub.model.ReadingType;
import org.coder229.datahub.model.Source;
import org.coder229.datahub.repository.ReadingRepository;
import org.coder229.datahub.repository.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;

@Service
@Transactional
public class ReadingService {
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    SourceRepository sourceRepository;

    @Autowired
    ReadingRepository readingRepository;

    public void process(Object payload, String sourceName) {
        try {
            if (payload instanceof String) {
                logger.debug(payload);
                Source source = sourceRepository.findByName(sourceName)
                        .orElseGet(() -> {
                            Source s = new Source();
                            s.name = sourceName;
                            return sourceRepository.save(s);
                        });
                List<Reading> readings = mapMessage(source, (String) payload);
            }
        } catch (IOException e) {
            logger.error("Error processing message", e);
        }
    }

    public List<Reading> mapMessage(Source source, String json) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode node = objectMapper.readTree(json);

        Instant instant = Instant.ofEpochSecond(node.findValue("epoch").asLong());
        ZonedDateTime dateTime = ZonedDateTime.ofInstant(instant, ZoneId.systemDefault());

        Reading humidityReading = new Reading();
        humidityReading.type = ReadingType.Humidity;
        humidityReading.source = source;
        humidityReading.dateTime = dateTime;
        humidityReading.value = Double.toString(node.findValue("hum").doubleValue());
        humidityReading.units = "%";

        Reading temperatureReading = new Reading();
        temperatureReading.type = ReadingType.Temperature;
        temperatureReading.source = source;
        temperatureReading.dateTime = dateTime;
        temperatureReading.value = Double.toString(node.findValue("tempf").doubleValue());
        temperatureReading.units = "F";

        return Arrays.asList(
                readingRepository.save(humidityReading),
                readingRepository.save(temperatureReading)
        );
    }

    public Page<Reading> getReadings(Pageable pageable) {
        return readingRepository.findAll(pageable);
    }
}
