package alexa.calculator.logic.interpreter.commands;

public abstract class FunctionCommand implements InterpreterCommand {

	@Override
	final public String getType() {
		return "function";
	}

	@Override
	final public boolean hasMutableValue() {
		return false;
	}

	@Override
	final public InterpreterCommand buildCopyWithValue(String value) {
		
		//ignoring value and using buildCopy()
		
		return buildCopy();
	}
	
	

}
