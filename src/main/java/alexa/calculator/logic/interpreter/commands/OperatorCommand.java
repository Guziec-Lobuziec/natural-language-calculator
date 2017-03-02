package alexa.calculator.logic.interpreter.commands;

public abstract class OperatorCommand implements InterpreterCommand {
	
	@Override
	public String getType() {
		return "operator";
	}

}
