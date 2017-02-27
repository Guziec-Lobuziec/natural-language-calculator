package alexa.calculator.logic.interpreter;

import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;

public class ProcessedScriptRuleBasedMapper implements LanguageToProcessedScriptMapper {
	
	protected static class PatternSearchTree {
		
		private Predicate<String> patternCheck;
		private PatternSearchTree leftNode;
		private PatternSearchTree rightNode;
		private MappingRule leafNode;
	}

	@Override
	public ProcessedScript map(String raw) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<String> supportedCommands() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public boolean addRule(MappingRule rule) {
		
	}

}
