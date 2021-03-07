package moe.iacg.miraiboot.utils;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import moe.iacg.miraiboot.exception.AuthenticationException;
import moe.iacg.miraiboot.rcon.Rcon;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author a8156
 */
@Component
public class RCONUtils {

    @NacosValue("${mc.rcon.host}")
    private String host;
    @NacosValue("${mc.rcon.port}")
    private int port;
    @NacosValue("${mc.rcon.password}")
    private String password;


    public String send(String command) {
        String result;
        try {
            Rcon rcon = new Rcon(host, port, password.getBytes());
            result = rcon.command(command);
        } catch (IOException | AuthenticationException e) {
            result = "Rcon连接出错," + e.getMessage();
        }
        return result;

    }
}
