package alexa.calculator.logic.interpreter;

public class ProcessedScript {
	
	public static class Builder {
		
		
		
	}
	
	private String rawCommands[];
	
	private int position = -2;
	
	protected ProcessedScript() {
	}
	
	public boolean goToNext() {
		
		if(position + 2 < rawCommands.length) {
			position+=2;
			return true;
		}
		
		return false;
	}
	
	public String getCommandName() {
		return rawCommands[position];
	}
	
	public String getCommandValue() {
		return rawCommands[position+1];
	}
	
	public int length() {
		return rawCommands.length/2;
	}
	
}
