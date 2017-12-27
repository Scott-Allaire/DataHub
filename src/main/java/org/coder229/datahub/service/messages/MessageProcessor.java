package org.coder229.datahub.service.messages;

import com.fasterxml.jackson.databind.JsonNode;
import org.coder229.datahub.model.Reading;
import org.coder229.datahub.model.Source;

import java.util.stream.Stream;

public interface MessageProcessor {
    boolean canProcess(String sourceName);
    Stream<Reading> process(Source source, JsonNode jsonNode);
}
