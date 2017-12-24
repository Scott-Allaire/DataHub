package org.coder229.datahub;

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

    @Value("${mqtt.url}")
    private String mqttUrl;

    @Value("${mqtt.topic}")
    private String mqttTopic;

    @Value("${mqtt.client.id}")
    private String mqttClientId;

    @Bean
    public MessageChannel messageChannel() {
        return new DirectChannel();
    }

    @Bean
    public MessageProducer messageProducer() {
        MqttPahoMessageDrivenChannelAdapter adapter =
                new MqttPahoMessageDrivenChannelAdapter(mqttUrl, mqttClientId, mqttTopic);

        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setOutputChannel(messageChannel());
        return adapter;
    }

    @Bean
    @ServiceActivator(inputChannel = "messageChannel")
    public MessageHandler messageHandler(ReadingService readingService) {
        return (Message<?> message) -> {
            // TODO client ID of sender?
            String sourceName = message.getHeaders().getId().toString();

            readingService.process(message.getPayload(), sourceName);
        };
    }
}
