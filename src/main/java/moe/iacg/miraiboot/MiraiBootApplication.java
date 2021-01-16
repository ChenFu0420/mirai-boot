package moe.iacg.miraiboot;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy(proxyTargetClass = true)
@SpringBootApplication
public class MiraiBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(MiraiBootApplication.class, args);
    }
}
