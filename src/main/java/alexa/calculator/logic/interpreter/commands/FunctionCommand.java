package alexa.calculator.logic.interpreter.commands;

public abstract class FunctionCommand extends InterpreterCommand {

	@Override
	public String getType() {
		return "function";
	}

}
