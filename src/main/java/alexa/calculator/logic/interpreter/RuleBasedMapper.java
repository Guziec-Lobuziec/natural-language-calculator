package alexa.calculator.logic.interpreter;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import alexa.calculator.logic.interpreter.commands.InterpreterCommand;

public class RuleBasedMapper implements LanguageToProcessedScriptMapper {
	
	public static class Builder {
		
		private List<MappingRule> buildingListOfMappingRules;
		
	}
	
	
	private Pattern masterPatternForMapping;
	private List<MappingRule> listOfMappingRules;
	
	
	@Override
	public Collection<List<InterpreterCommand>> map(String raw) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<InterpreterCommand> supportedCommands() {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected RuleBasedMapper(
			Pattern masterPatternForMapping,
			List<MappingRule> listOfMappingRules
		) {
		this.masterPatternForMapping = masterPatternForMapping;
		this.listOfMappingRules = listOfMappingRules;
	}

}
