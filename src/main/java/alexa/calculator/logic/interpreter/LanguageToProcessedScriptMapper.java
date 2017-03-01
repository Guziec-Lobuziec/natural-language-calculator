package alexa.calculator.logic.interpreter;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import alexa.calculator.logic.interpreter.commands.InterpreterCommand;

public interface LanguageToProcessedScriptMapper {
	
	public Collection<List<InterpreterCommand>> map(String raw);
	
	public Set<InterpreterCommand> supportedCommands();
	
}
