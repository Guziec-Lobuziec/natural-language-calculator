package alexa.calculator.logic.interpreter;

import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface LanguageToProcessedScriptMapper {
	
	public Collection<List<InterpreterCommand>> map(String raw);
	
	public Set<InterpreterCommand> supportedCommands();
	
}
