package alexa.calculator.logic.interpreter.commands;

public interface InterpreterCommand {
	
	public String getValue();
	
	public String getType();
	
	public boolean hasMutableValue();
	
	public InterpreterCommand buildCopy();
	
	public InterpreterCommand buildCopyWithValue(String value) throws InterpreterCommandException;
	
	
}
