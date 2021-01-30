package moe.iacg.miraiboot.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Configuration
public class TelegramConfig {
    @Value("${spring.profiles.active}")
    private String profilesActive;

    @Bean
    DefaultBotOptions defaultBotOptions() {
        if ("dev".equals(profilesActive)) {
            DefaultBotOptions defaultBotOptions = new DefaultBotOptions();
            defaultBotOptions.setProxyHost("127.0.0.1");
            defaultBotOptions.setProxyType(DefaultBotOptions.ProxyType.HTTP);
            defaultBotOptions.setProxyPort(1080);
            return defaultBotOptions;
        }
        return new DefaultBotOptions();
    }

}
