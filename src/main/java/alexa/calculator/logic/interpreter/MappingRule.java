package alexa.calculator.logic.interpreter;

public class MappingRule {
	
	private String preCommand;
	private String postCommand;
	
	private String patternToSearch;
	
	private String outputCommandName;
	private String outputValue;
	
	public MappingRule(
			String patternToSearch,
			String outputCommandName) {
		
		this.patternToSearch = patternToSearch;
		this.outputCommandName = outputCommandName;
		
	}
	
	public MappingRule setPreCommand(String commandName) {
		this.preCommand = commandName;
		return this;
	}
	
	public MappingRule setPostCommand(String commandName) {
		this.postCommand = commandName;
		return this;
	}
	
	public MappingRule setOutputValue(String commandValue) {
		this.outputValue = commandValue;
		return this;
	}

	public String getPreCommand() {
		return preCommand;
	}

	public String getPostCommand() {
		return postCommand;
	}

	public String getPatternToSearch() {
		return patternToSearch;
	}

	public String getOutputCommandName() {
		return outputCommandName;
	}

	public String getOutputValue() {
		return outputValue;
	}
	
	
}
