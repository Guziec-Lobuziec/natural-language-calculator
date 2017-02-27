package alexa.calculator.logic.interpreter;

import java.util.Set;

public interface LanguageToProcessedScriptMapper {
	
	public ProcessedScript map(String raw);
	
	public Set<String> supportedCommands();
	
}
