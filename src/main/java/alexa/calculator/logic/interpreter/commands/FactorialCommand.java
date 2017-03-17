package alexa.calculator.logic.interpreter.commands;

public class FactorialCommand extends FunctionCommand {

	@Override
	public String getValue() {
		return "factorial";
	}

	@Override
	public InterpreterCommand buildCopy() {
		return new FactorialCommand();
	}
	
}
