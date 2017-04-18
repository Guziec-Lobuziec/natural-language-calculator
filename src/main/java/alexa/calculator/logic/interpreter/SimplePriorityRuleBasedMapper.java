package alexa.calculator.logic.interpreter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import alexa.calculator.logic.interpreter.commands.InterpreterCommand;
import alexa.calculator.logic.interpreter.commands.InterpreterCommandException;

public class SimplePriorityRuleBasedMapper implements LanguageToProcessedScriptMapper {
	
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
		
		public SimplePriorityRuleBasedMapper build() {
			
			int initialCapacityOfContainers = 
					this.buildersListsOfRulesWithPriority.values().size()
					*this.buildersListsOfRulesWithPriority.firstEntry().getValue().size();
			
			List<List<MappingRule>> listOfListsOfMappingRules
				= new ArrayList<>( initialCapacityOfContainers );
			
			Map<String,Integer> regexToIndexInList
				= new Hashtable<>( initialCapacityOfContainers );
			
			Integer key = this.buildersListsOfRulesWithPriority.firstKey();
			
			do
			{
				
				for(MappingRule singleRule : this.buildersListsOfRulesWithPriority.get(key))
				{
					
					if(regexToIndexInList.containsKey(singleRule.getRegex())) {
						
						listOfListsOfMappingRules
							.get( regexToIndexInList.get( singleRule.getRegex() ) )
								.add( singleRule );
					}
					else {
						
						listOfListsOfMappingRules.add(new ArrayList<>(1));
						
						listOfListsOfMappingRules
							.get(listOfListsOfMappingRules.size()-1)
								.add(singleRule);
						
						regexToIndexInList.put(
									singleRule.getRegex(),
									listOfListsOfMappingRules.size()-1
								);
						
					}
					
					
				}
				
				key = this.buildersListsOfRulesWithPriority.higherKey(key);
				
			}while(key != null);
			
			Map<String,List<MappingRule>> buildersGroupIdsToMappingRules
				= new Hashtable<String, List<MappingRule>> (listOfListsOfMappingRules.size());
			
			String masterRegex = buildMasterRegexAndMappingRuleMap(
						listOfListsOfMappingRules,
						buildersGroupIdsToMappingRules, 
						""
					);
			
			return new SimplePriorityRuleBasedMapper(
					Pattern.compile(masterRegex), 
					buildersGroupIdsToMappingRules
				);
			
		}
		
		private String buildMasterRegexAndMappingRuleMap(List<List<MappingRule>> buildersList, Map<String, List<MappingRule>> mappingRulesMap, String groupkey) {
			
			if(buildersList.size()>2)
				return "(?<" + GroupNamePrefix + 0 + groupkey + ">"
						+buildMasterRegexAndMappingRuleMap(
								
								buildersList.subList(0, buildersList.size()/2),
								mappingRulesMap, 0 + groupkey 
								
							)
						+")|"
						+"(?<" + GroupNamePrefix + 1 + groupkey + ">"
						+buildMasterRegexAndMappingRuleMap(
								
								buildersList.subList(buildersList.size()/2, buildersList.size()),
								mappingRulesMap, 1 + groupkey 
								
							)
						+")";
			else
				if(buildersList.size()>1) {
					
					mappingRulesMap.put(0 + groupkey, buildersList.get(0));
					mappingRulesMap.put(1 + groupkey, buildersList.get(1));
					
					return "(?<" + GroupNamePrefix + 0 + groupkey + ">"
							+buildersList.get(0).get(0).getRegex()
							+")|"
							+"(?<" + GroupNamePrefix + 1 + groupkey + ">"
							+buildersList.get(1).get(0).getRegex()
							+")";
				}
				else
					if(buildersList.isEmpty())
						return "";
					else {
						
						mappingRulesMap.put(0 + groupkey, buildersList.get(0));
						
						return "(?<" + GroupNamePrefix + 0 + groupkey + ">"
								+buildersList.get(0).get(0).getRegex()
								+")";
					}
			
		}
		
		
	}
	
	public static final String SourceGroupNamePrefix = "grsrc";
	public static final String GroupNamePrefix = "groupkey";
	
	private final Pattern masterPatternForMapping;
	private final Map<String,List<MappingRule>> groupIdsToMappingRules;
	
	
	@Override
	public Collection<List<InterpreterCommand>> map(String raw) {
		
		Set<List<InterpreterCommand>> outputOfMapping = new HashSet<>();
		
		outputOfMapping.add(mapSubstringToInterpreterCommands(raw));
		
		return outputOfMapping;
	}

	@Override
	public Set<InterpreterCommand> supportedCommands() {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected SimplePriorityRuleBasedMapper(
			Pattern masterPatternForMapping,
			Map<String,List<MappingRule>> groupIdsToMappingRules
		) {
		this.masterPatternForMapping = masterPatternForMapping;
		this.groupIdsToMappingRules = groupIdsToMappingRules;
	}
	
	private String findHighestPriorityMatch(String parentGroupName, Matcher matcher){
		
		if(
				parentGroupName.length() >= 
				Integer.highestOneBit(groupIdsToMappingRules.size())
			)
			return parentGroupName;
		
		try
		{
			if(matcher.group(GroupNamePrefix+0+parentGroupName) != null)
				if(!matcher.group(GroupNamePrefix+0+parentGroupName).isEmpty())
					return findHighestPriorityMatch( 0+parentGroupName , matcher);
		}
		catch (IllegalArgumentException groupException) {
			
			return parentGroupName;
		}
		
		if(!matcher.group(GroupNamePrefix+1+parentGroupName).isEmpty())
			return findHighestPriorityMatch( 1+parentGroupName, matcher);
		
		return "";
		
	}
	
	private List<InterpreterCommand> mapSubstringToInterpreterCommands(String rawSubstring) {
		
		if(rawSubstring.isEmpty())
			return new LinkedList<>();
		
		Matcher matcher = this.masterPatternForMapping.matcher(rawSubstring);
		
		if(matcher.find()) {
			
			String groupId = findHighestPriorityMatch("", matcher);
			
			List<InterpreterCommand> output = new LinkedList<>();
			
			output.addAll(
					mapSubstringToInterpreterCommands(
							rawSubstring.substring(0, matcher.start(groupId))
						)
				);
			
			try {
				output.add(
					this.groupIdsToMappingRules
						.get(groupId).get(0)
							.getOutputCommand().buildCopyWithValue(matcher.group(groupId))
				);
			} catch (InterpreterCommandException e) {
				
				output.addAll(
						mapSubstringToInterpreterCommands(
								rawSubstring.substring(matcher.start(groupId))
							)
					);
				
			}
			
			output.addAll(
					mapSubstringToInterpreterCommands(
							rawSubstring.substring(matcher.end(groupId))
						)
				);
			
			return output;
			
		}
		
		return new LinkedList<>();
		
	}

}
