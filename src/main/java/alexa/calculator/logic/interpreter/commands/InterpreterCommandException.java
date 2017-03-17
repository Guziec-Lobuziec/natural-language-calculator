package alexa.calculator.logic.interpreter.commands;

public class InterpreterCommandException extends Exception {

	private static final long serialVersionUID = 1L;
	
	public InterpreterCommandException(String message) {
		super(message);
	}
	
	public InterpreterCommandException(String message, Throwable cause) {
		super(message, cause);
	}
}
