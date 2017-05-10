package alexa.calculator.logic.interpreter.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DecimalNumberCommand implements InterpreterCommand {
	
	String numberValue;
	
	private DecimalNumberCommand() {
		numberValue = "0";
	}
	
	public DecimalNumberCommand(int value) {
		this.numberValue = Integer.toString(value);
	}
	
	public DecimalNumberCommand(String value) throws InterpreterCommandException {
		
		Matcher numberMatcher = Pattern
						.compile(
							"\\s*(?<number>(-)?\\d+((\\.|,)\\d+)?)\\s*"
						).matcher(value);
		
		if(numberMatcher.find()) {
			
			String processedValue = numberMatcher.group("number");
			
			Matcher commaMatcher
				= Pattern.compile(",")
					.matcher(processedValue);
			this.numberValue = commaMatcher.replaceAll(".");
		}
		else {
			throw new InterpreterCommandException("DecimalNumberCommand: Given string \'"+value+"\' is not a decimal number");
		}
		
	}
	
	@Override
	public String getValue() {
		return numberValue;
	}

	@Override
	public String getType() {
		return "number";
	}

	@Override
	public boolean hasMutableValue() {
		return true;
	}

	@Override
	public InterpreterCommand buildCopy() {
		DecimalNumberCommand out = new DecimalNumberCommand();
		out.numberValue = this.numberValue;
		return out;
	}

	@Override
	public InterpreterCommand buildCopyWithValue(String value) throws InterpreterCommandException {
		return new DecimalNumberCommand(value);
	}

}
