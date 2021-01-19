package moe.iacg.miraiboot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Configuration
public class BotConfig {

    @Bean
    DefaultBotOptions defaultBotOptions(){
        DefaultBotOptions defaultBotOptions = new DefaultBotOptions();
//        defaultBotOptions.setProxyHost("127.0.0.1");
//        defaultBotOptions.setProxyType(DefaultBotOptions.ProxyType.HTTP);
//        defaultBotOptions.setProxyPort(1080);
        return defaultBotOptions;
    }
}
