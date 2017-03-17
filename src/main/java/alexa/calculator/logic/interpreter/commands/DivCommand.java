package alexa.calculator.logic.interpreter.commands;

public class DivCommand extends OperatorCommand {

	@Override
	public String getValue() {
		return "/";
	}

	@Override
	public InterpreterCommand buildCopy() {
		return new DivCommand();
	}

}
