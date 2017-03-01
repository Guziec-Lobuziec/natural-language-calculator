package alexa.calculator.logic.interpreter.commands;

public abstract class OperatorCommand extends InterpreterCommand {
	
	@Override
	public String getType() {
		return "oper";
	}

}
