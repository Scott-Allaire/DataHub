package org.coder229.datahub;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.coder229.datahub.service.ReadingService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

@Configuration
public class MqttConfig {
    protected final Log logger = LogFactory.getLog(getClass());

    @Value("${mqtt.url}")
    private String mqttUrl;

    @Value("${mqtt.topics}")
    private String mqttTopics;

    @Value("${mqtt.client.id}")
    private String mqttClientId;

    @Bean
    public MessageChannel messageChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer messageProducer() {
        String[] topics = mqttTopics.split(",");
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(mqttUrl, mqttClientId, topics);

        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setOutputChannel(messageChannel());
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "messageChannel")
    public MessageHandler messageHandler(ReadingService readingService) {
        return (Message<?> message) -> {
            String sourceName = (String) message.getHeaders().get("mqtt_topic");
            logger.info("Received message from " + sourceName);
            readingService.process(message.getPayload(), sourceName);
        };
    }
}
