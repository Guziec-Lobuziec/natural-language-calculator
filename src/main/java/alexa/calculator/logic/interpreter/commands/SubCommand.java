package alexa.calculator.logic.interpreter.commands;

public class SubCommand extends OperatorCommand {

	@Override
	public String getValue() {
		return "-";
	}

	@Override
	public InterpreterCommand buildCopy() {
		return new SubCommand();
	}

}
