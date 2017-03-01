package alexa.calculator.logic.interpreter;

import java.util.HashMap;
import java.util.Map;

import alexa.calculator.logic.interpreter.commands.InterpreterCommand;

public class MappingRule {
	
	private String regex;
	private InterpreterCommand outputCommand;
	
	/*<command type + command value , required/prohibited>*/
	private Map<String, Boolean> preCommands = new HashMap<String, Boolean>();
	private Map<String, Boolean> postCommands = new HashMap<String, Boolean>();
	boolean hasRequiredPreCommands = false;
	boolean hasRequiredPostCommands = false;
	
	
	public MappingRule(String regex, InterpreterCommand outputCommand) {
		this.regex = regex;
		this.outputCommand = outputCommand;
	}
	
	
	
	public String getRegex() {
		return regex;
	}

	

	public InterpreterCommand getOutputCommand() {
		return outputCommand;
	}



	public boolean addRequiredPrefixCommand(InterpreterCommand command, boolean includeCommandValue) {
		
		if(preCommands.containsKey(command.getType() +"@"+ (includeCommandValue ? command.getValue() : "")))
			return false;
		
		preCommands.put(command.getType() +"@"+ (includeCommandValue ? command.getValue() : ""), true);
		hasRequiredPreCommands = true;
		
		return true;
		
	}
	
	
	
	public boolean addProhibitedPrefixCommand(InterpreterCommand command, boolean includeCommandValue) {
		
		if(preCommands.containsKey(command.getType() +"@"+ (includeCommandValue ? command.getValue() : "")))
			return false;
		
		preCommands.put(command.getType() +"@"+ (includeCommandValue ? command.getValue() : ""), false);
		
		return true;
		
	}
	
	
	
	public boolean addRequiredSuffixCommand(InterpreterCommand command, boolean includeCommandValue) {
		
		if(postCommands.containsKey(command.getType() +"@"+ (includeCommandValue ? command.getValue() : "")))
			return false;
		
		postCommands.put(command.getType() +"@"+ (includeCommandValue ? command.getValue() : ""), true);
		hasRequiredPostCommands = true;
		
		return true;
		
	}
	
	
	
	public boolean addProhibitedSuffixCommand(InterpreterCommand command, boolean includeCommandValue) {
		
		if(postCommands.containsKey(command.getType() +"@"+ (includeCommandValue ? command.getValue() : "")))
			return false;
		
		postCommands.put(command.getType() +"@"+ (includeCommandValue ? command.getValue() : ""), false);
		
		return true;
		
	}
	
	
	
	public boolean checkIfPrefixCommandIsValid(InterpreterCommand command) {
		
		if(preCommands.isEmpty())
			return true;
		
		Boolean typePermission = preCommands.get(command.getType()+"@");
		Boolean typeAndValuePermission = preCommands.get(command.getType()+"@"+command.getValue());
		
		if(hasRequiredPreCommands) {
			
			if(typePermission != null) {
				
				if(typeAndValuePermission != null)
					return typeAndValuePermission;
				
				return typePermission;
			}
			
			if(typeAndValuePermission != null)
				return typeAndValuePermission;
			
			return false;
		}
		else {
			if( (typePermission == null)&&(typeAndValuePermission == null) )
				return true;
			
			return false;
		}
		
	}
	
	
	
	public boolean checkIfSuffixCommandIsValid(InterpreterCommand command) {
		
		if(postCommands.isEmpty())
			return true;
		
		Boolean typePermission = postCommands.get(command.getType()+"@");
		Boolean typeAndValuePermission = postCommands.get(command.getType()+"@"+command.getValue());
		
		if(hasRequiredPostCommands) {
			
			if(typePermission != null) {
				
				if(typeAndValuePermission != null)
					return typeAndValuePermission;
				
				return typePermission;
			}
			
			if(typeAndValuePermission != null)
				return typeAndValuePermission;
			
			return false;
		}
		else {
			if( (typePermission == null)&&(typeAndValuePermission == null) )
				return true;
			
			return false;
		}
	}
	
}
