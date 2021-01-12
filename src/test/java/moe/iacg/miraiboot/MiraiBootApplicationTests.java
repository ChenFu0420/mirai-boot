package moe.iacg.miraiboot;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MiraiBootApplicationTests {

	@NacosValue(value = "${mysql.url}")
	private String url;

	@Test
	void contextLoads() {
		System.out.println(url);
	}

}
