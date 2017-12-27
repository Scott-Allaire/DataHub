package org.coder229.datahub.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.coder229.datahub.model.Reading;
import org.coder229.datahub.model.Source;
import org.coder229.datahub.repository.ReadingRepository;
import org.coder229.datahub.repository.SourceRepository;
import org.coder229.datahub.service.messages.MessageProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ReadingService {
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    SourceRepository sourceRepository;

    @Autowired
    ReadingRepository readingRepository;

    @Autowired
    List<MessageProcessor> messageProcessors;

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

                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode jsonNode = objectMapper.readTree((String) payload);

                List<Reading> readings = messageProcessors.stream()
                        .filter(messageProcessor -> messageProcessor.canProcess(sourceName))
                        .flatMap(messageProcessor -> messageProcessor.process(source, jsonNode))
                        .collect(Collectors.toList());
            }
        } catch (IOException e) {
            logger.error("Error processing message", e);
        }
    }

    public Page<Reading> getReadings(Pageable pageable) {
        return readingRepository.findAll(pageable);
    }
}
