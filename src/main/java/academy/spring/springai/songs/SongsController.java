package academy.spring.springai.songs;

import org.springframework.ai.chat.ChatClient;
import org.springframework.ai.chat.Generation;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.parser.BeanOutputParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/songs")
public class SongsController {



	// Text prompts that is used
	private static final String SIMPLE_PROMPT = """
		Tell me a joke.
		""";

	private static final String PARAMETER_PROMPT = """
		Tell me a joke about {var}.?
		""";





	// Inject ChatClient
	private final ChatClient chatClient;

	public SongsController(ChatClient chatClient) {
		this.chatClient = chatClient;
	}


	// String Prompt
	@GetMapping("/stringprompt")
	public String simplePrompt() {
		return chatClient.call(SIMPLE_PROMPT);
	}

	// Prompt with parameter
	@GetMapping("/parameterprompt/{var}")
	public String parameterPrompt(@PathVariable("var") String variable) {
		PromptTemplate template = new PromptTemplate(PARAMETER_PROMPT);
		template.add("var", variable);
		return chatClient.call(template.render());
	}

	// Return object
	@GetMapping("/objectreturn/{var}")
	public TopSong objectReturnPrompt(@PathVariable("var") String variable) {

		BeanOutputParser<TopSong> parser = new BeanOutputParser<>(TopSong.class);
		String stringPrompt = PARAMETER_PROMPT + """
  				{format}
  				""";
		PromptTemplate template = new PromptTemplate(stringPrompt);
		template.add("var", variable);
		template.add("format", parser.getFormat());
		//System.out.println("PARSER FORMAT: " + parser.getFormat());

		Prompt prompt = template.create();
		Generation generation = chatClient.call(prompt).getResult();
		return parser.parse(generation.getOutput().getContent());
	}

}
