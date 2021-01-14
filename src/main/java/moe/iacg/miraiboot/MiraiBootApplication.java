package moe.iacg.miraiboot;


import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
//@EnableNacosConfig(readConfigTypeFromDataId = false)
@NacosPropertySource(dataId = "mirai-boot-client.properties", autoRefreshed = true,type = ConfigType.PROPERTIES)
public class MiraiBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(MiraiBootApplication.class, args);
    }
}
