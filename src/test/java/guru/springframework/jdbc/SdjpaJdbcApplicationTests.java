package guru.springframework.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SdjpaJdbcApplicationTests {

	@Test
	void contextLoads(ApplicationContext appCtx) {
		assertThat(appCtx).isNotNull();
	}

}
