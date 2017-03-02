package alexa.calculator;

import alexa.calculator.logic.interpreter.MappingRule;
import alexa.calculator.logic.interpreter.commands.AddCommand;
import static org.junit.Assert.*;

import org.junit.Test;

public class MappingRuleTest {
	
	@Test
	public void testAddingProhibitedPrefixAndSuffix() {
		
		MappingRule rule1 = new MappingRule("\\s+add\\s+", new AddCommand());
		
		assertTrue("added prefix was not unique", rule1.addProhibitedPrefixCommand(new AddCommand(), false));
		assertTrue("added suffix was not unique", rule1.addProhibitedSuffixCommand(new AddCommand(), false));
		
		assertTrue("added prefix was not unique", rule1.addProhibitedPrefixCommand(new AddCommand(), true));
		assertTrue("added suffix was not unique", rule1.addProhibitedSuffixCommand(new AddCommand(), true));
		
		assertFalse("prefix should already exist", rule1.addProhibitedPrefixCommand(new AddCommand(), false));
		assertFalse("suffix should already exist", rule1.addProhibitedSuffixCommand(new AddCommand(), false));
		
		assertFalse("prefix should already exist", rule1.addProhibitedPrefixCommand(new AddCommand(), true));
		assertFalse("suffix should already exist", rule1.addProhibitedSuffixCommand(new AddCommand(), true));
		
	}
	
	@Test
	public void testAddingRequiredPrefixAndSuffix() {
		
		MappingRule rule1 = new MappingRule("\\s+add\\s+", new AddCommand());
		
		assertTrue("added prefix was not unique", rule1.addRequiredPrefixCommand(new AddCommand(), false));
		assertTrue("added suffix was not unique", rule1.addRequiredSuffixCommand(new AddCommand(), false));
		
		assertTrue("added prefix was not unique", rule1.addRequiredPrefixCommand(new AddCommand(), true));
		assertTrue("added suffix was not unique", rule1.addRequiredSuffixCommand(new AddCommand(), true));
		
		assertFalse("prefix should already exist", rule1.addRequiredPrefixCommand(new AddCommand(), false));
		assertFalse("suffix should already exist", rule1.addRequiredSuffixCommand(new AddCommand(), false));
		
		assertFalse("prefix should already exist", rule1.addRequiredPrefixCommand(new AddCommand(), true));
		assertFalse("suffix should already exist", rule1.addRequiredSuffixCommand(new AddCommand(), true));
		
	}
	
	public void testCheckingPrefixAndSuffix_AgainstOnlyProhibitedSamples() {
		
	}
	
}
