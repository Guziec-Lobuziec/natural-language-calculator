package alexa.calculator;

import java.util.Collection;
import java.util.List;

import org.junit.Test;

import alexa.calculator.logic.interpreter.MappingRule;
import alexa.calculator.logic.interpreter.SimplePriorityRuleBasedMapper;
import alexa.calculator.logic.interpreter.commands.AddCommand;
import alexa.calculator.logic.interpreter.commands.DecimalNumberCommand;
import alexa.calculator.logic.interpreter.commands.DivCommand;
import alexa.calculator.logic.interpreter.commands.FactorialCommand;
import alexa.calculator.logic.interpreter.commands.InterpreterCommand;
import alexa.calculator.logic.interpreter.commands.MulCommand;
import alexa.calculator.logic.interpreter.commands.SubCommand;

import static org.junit.Assert.*;

public class SimplePriorityRuleBasedMapperTest {
	
	SimplePriorityRuleBasedMapper.Builder builder = new SimplePriorityRuleBasedMapper.Builder();
	
	@Test
	public void testMapperAndBuilder() {
		
		builder.addNewRuleWithPriority(new MappingRule("(^|\\s+)(-)?\\d+((\\.|,)\\d+)?(\\s+|$)", new DecimalNumberCommand(0)), 1);
		
		//test with one rule
		{
			SimplePriorityRuleBasedMapper mapper = builder.build();
			
			Collection<List<InterpreterCommand>> output = mapper.map("0.78 factorial of 123");
			
			for(List<InterpreterCommand> commands : output)
			{
				
				assertEquals("Wrong size", 2, commands.size());
				
				assertEquals("Wrong value","0.78",commands.get(0).getValue());
				assertEquals("Wrong value",new DecimalNumberCommand(123).getValue(),commands.get(1).getValue());
				
			}
		}
		
		builder.addNewRule(new MappingRule("[Ff]actorial(\\s+of)?", new FactorialCommand()));
		
		//test with two rules
		{
			SimplePriorityRuleBasedMapper mapper = builder.build();
			
			Collection<List<InterpreterCommand>> output = mapper.map("0.78 factorial of 123 22,9");
			
			for(List<InterpreterCommand> commands : output)
			{
				
				assertEquals("Wrong size", 4, commands.size());
				
				assertEquals("Wrong value","0.78",commands.get(0).getValue());
				assertEquals("Wrong value",new FactorialCommand().getValue(),commands.get(1).getValue());
				assertEquals("Wrong value",new DecimalNumberCommand(123).getValue(),commands.get(2).getValue());
				assertEquals("Wrong value", "22.9", commands.get(3).getValue());
				
			}
		}
		
		builder.addNewRule(new MappingRule("\\s*([Pp]lus|[Aa]dd)\\s*", new AddCommand()));
		builder.addNewRule(new MappingRule("\\s*([Tt]ime(s)?)|([Mm]ultiplied(\\s+[Bb]y)?)\\s*", new MulCommand()));
		builder.addNewRule(new MappingRule("\\s*[Mm]inus\\s*", new SubCommand()));
		builder.addNewRule(new MappingRule("\\s*[Dd]ivided(\\s+[Bb]y)?\\s*", new DivCommand()));
		
		//more than two
		{
			SimplePriorityRuleBasedMapper mapper = builder.build();
			
			Collection<List<InterpreterCommand>> output = mapper.map("factorial of 3 times 2 plus 5,09");
			
			for(List<InterpreterCommand> commands : output)
			{
				
				assertEquals("Wrong size", 6, commands.size());
				
				assertEquals("Wrong value",new FactorialCommand().getValue(),commands.get(0).getValue());
				assertEquals("Wrong value",new DecimalNumberCommand(3).getValue(),commands.get(1).getValue());
				assertEquals("Wrong value",new MulCommand().getValue(), commands.get(2).getValue());
				assertEquals("Wrong value",new DecimalNumberCommand(2).getValue(),commands.get(3).getValue());
				assertEquals("Wrong value",new AddCommand().getValue(), commands.get(4).getValue());
				assertEquals("Wrong value","5.09",commands.get(5).getValue());
			}
		}
	}
	
	
	
	
}
