package academy.spring.springai.songs;

import org.springframework.ai.image.ImageClient;
import org.springframework.ai.openai.OpenAiImageClient;
import org.springframework.ai.openai.api.OpenAiImageApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SongsApplication {

	public static void main(String[] args) {
		SpringApplication.run(SongsApplication.class, args);
	}

	// Create new ImageClient Bean
	@Bean
	ImageClient imageClient(@Value("${spring.ai.openai.api-key}") String apiKey) {
		return new OpenAiImageClient(new OpenAiImageApi(apiKey));
	}

}
