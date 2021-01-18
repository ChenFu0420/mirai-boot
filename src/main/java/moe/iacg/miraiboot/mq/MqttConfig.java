package moe.iacg.miraiboot.mq;


import com.alibaba.nacos.api.config.annotation.NacosValue;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.IntegrationComponentScan;

@Configuration
@IntegrationComponentScan
@Data
public class MqttConfig {

    @NacosValue("${spring.mqtt.username}")
    private String username;

    @NacosValue("${spring.mqtt.password}")
    private String password;

    @NacosValue("${spring.mqtt.url}")
    private String hostUrl;

    @NacosValue("${spring.mqtt.client.id}")
    private String clientId;

    @NacosValue("${spring.mqtt.default.topic}")
    private String defaultTopic;

}