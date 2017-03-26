package alexa.calculator.logic.interpreter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Pattern;

import alexa.calculator.logic.interpreter.commands.InterpreterCommand;

public class SimpleIterativeRuleBasedMapper implements LanguageToProcessedScriptMapper {
	
	public static class Builder {
		
		private TreeMap<Integer, List<MappingRule>> buildersListsOfRulesWithPriority = new TreeMap<>();
		
		public void addNewRuleWithPriority(MappingRule rule, int priority) {
			
			if(!buildersListsOfRulesWithPriority.containsKey(priority))
				buildersListsOfRulesWithPriority.put(priority, new LinkedList<>());
			
			buildersListsOfRulesWithPriority.get(priority).add(rule);
			
		}
		
		public void addNewRule(MappingRule rule) {
			addNewRuleWithPriority(rule, 0);
		}
		
		public SimpleIterativeRuleBasedMapper build() {
			
			int initialCapacityOfContainers = 
					this.buildersListsOfRulesWithPriority.values().size()
					*this.buildersListsOfRulesWithPriority.firstEntry().getValue().size();
			
			List<List<MappingRule>> buildersGroupNumbersToMappingRules
				= new ArrayList<>( initialCapacityOfContainers );
			
			Map<String,Integer> regexToIndexInList
				= new HashMap<>( initialCapacityOfContainers );
			
			Integer key = this.buildersListsOfRulesWithPriority.firstKey();
			
			do
			{
				
				for(MappingRule singleRule : this.buildersListsOfRulesWithPriority.get(key))
				{
					
					if(regexToIndexInList.containsKey(singleRule.getRegex())) {
						
						buildersGroupNumbersToMappingRules
							.get( regexToIndexInList.get( singleRule.getRegex() ) )
								.add( singleRule );
					}
					else {
						
						buildersGroupNumbersToMappingRules.add(new ArrayList<>(1));
						
						buildersGroupNumbersToMappingRules
							.get(buildersGroupNumbersToMappingRules.size()-1)
								.add(singleRule);
						
						regexToIndexInList.put(
									singleRule.getRegex(),
									buildersGroupNumbersToMappingRules.size()-1
								);
						
					}
					
					
				}
				
				key = this.buildersListsOfRulesWithPriority.higherKey(key);
				
			}while(key != null);
			
			
			return new SimpleIterativeRuleBasedMapper(
					Pattern.compile(buildMasterRegex(buildersGroupNumbersToMappingRules, "")), 
					buildersGroupNumbersToMappingRules
				);
			
		}
		
		private String buildMasterRegex(List<List<MappingRule>> buildersList, String groupkey) {
			
			if(buildersList.size()>2)
				return "(?<" + GroupNamePrefix + 0 + groupkey + ">"
						+buildMasterRegex(
								
								buildersList.subList(0, buildersList.size()/2),
								0 + groupkey 
								
							)
						+")|"
						+"(?<" + GroupNamePrefix + 1 + groupkey + ">"
						+buildMasterRegex(
								
								buildersList.subList(buildersList.size()/2, buildersList.size()),
								1 + groupkey 
								
							)
						+")";
			else
				if(buildersList.size()>1)
					return "(?<" + GroupNamePrefix + 0 + groupkey + ">"
							+buildersList.get(0).get(0).getRegex()
							+")|"
							+"(?<" + GroupNamePrefix + 1 + groupkey + ">"
							+buildersList.get(1).get(0).getRegex()
							+")";
				else
					if(buildersList.isEmpty())
						return "";
					else
						return "(?<" + GroupNamePrefix + 0 + groupkey + ">"
								+buildersList.get(0).get(0).getRegex()
								+")";
			
		}
		
		
	}
	
	public static final String GroupNamePrefix = "groupkey";
	
	private final Pattern masterPatternForMapping;
	private final List<List<MappingRule>> groupNumbersToMappingRules;
	
	
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
	
	protected SimpleIterativeRuleBasedMapper(
			Pattern masterPatternForMapping,
			List<List<MappingRule>> groupNumbersToMappingRules
		) {
		this.masterPatternForMapping = masterPatternForMapping;
		this.groupNumbersToMappingRules = groupNumbersToMappingRules;
	}

}
