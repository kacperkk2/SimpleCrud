package klimczuk.kacper.simplecrud;

import klimczuk.kacper.simplecrud.controllers.QuoteController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SimpleCrudApplicationTests {

	@Autowired
	private QuoteController quoteController;

	@Test
	void contextLoads() {
		assertThat(quoteController).isNotNull();
	}
}
