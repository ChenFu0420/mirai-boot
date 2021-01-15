package moe.iacg.miraiboot.interceptor;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import net.lz1998.pbbot.handler.BotSessionInterceptor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Primary
@Component
@Log4j2
public class MyWsInterceptor extends BotSessionInterceptor {
    @SneakyThrows
    @Override
    public boolean checkSession(@NotNull WebSocketSession session) {
        var headers = session.getHandshakeHeaders();
        var botId = headers.getFirst("x-self-id");
        log.info(headers);
        // 正常连接
        return true;
    }
}
