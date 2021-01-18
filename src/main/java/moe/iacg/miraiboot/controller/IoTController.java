package moe.iacg.miraiboot.controller;

import moe.iacg.miraiboot.mq.MqttGatewayApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/IoT")
public class IoTController {

//    @Autowired
//    MqttGatewayApi mqttGatewayApi;

    @RequestMapping("/switch")
    public String door(String inTopic, String status) {
        try {
//            mqttGatewayApi.sendToMqtt(status, inTopic);
        } catch (Exception e) {
            e.printStackTrace();
            return e.getMessage();
        }
        return "ok";
    }
}
