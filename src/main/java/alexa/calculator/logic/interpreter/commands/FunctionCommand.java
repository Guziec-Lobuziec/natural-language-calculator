package alexa.calculator.logic.interpreter.commands;

public abstract class FunctionCommand implements InterpreterCommand {

	@Override
	public String getType() {
		return "function";
	}

}
