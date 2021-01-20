package moe.iacg.miraiboot.mq;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

/**
 * @author Ghost
 */
//@Component
//@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface MqttGatewayApi {
    void sendToMqtt(String data, @Header(MqttHeaders.TOPIC) String topic);
}
