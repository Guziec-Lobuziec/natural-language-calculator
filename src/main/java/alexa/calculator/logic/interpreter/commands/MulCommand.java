package alexa.calculator.logic.interpreter.commands;

public class MulCommand extends OperatorCommand {

	@Override
	public String getValue() {
		return "*";
	}

	@Override
	public InterpreterCommand buildCopy() {
		return new MulCommand();
	}

}
