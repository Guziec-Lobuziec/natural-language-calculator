package alexa.calculator;

import alexa.calculator.logic.interpreter.MappingRule;
import alexa.calculator.logic.interpreter.commands.AddCommand;
import alexa.calculator.logic.interpreter.commands.FactorialCommand;
import alexa.calculator.logic.interpreter.commands.SubCommand;

import static org.junit.Assert.*;

import org.junit.Test;

public class MappingRuleTest {
	
	@Test
	public void testAddingProhibitedPrefixAndSuffix() {
		
		MappingRule rule1 = new MappingRule("\\s+add\\s+", new AddCommand());
		
		assertTrue("added prefix was not unique", rule1.addProhibitedPrefixCommand(new AddCommand(), false));
		assertTrue("added suffix was not unique", rule1.addProhibitedSuffixCommand(new AddCommand(), false));
		
		assertFalse("prefix should already exist", rule1.addProhibitedPrefixCommand(new AddCommand(), false));
		assertFalse("suffix should already exist", rule1.addProhibitedSuffixCommand(new AddCommand(), false));
		
		
		
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
		
		assertFalse("prefix should already exist", rule1.addRequiredPrefixCommand(new AddCommand(), false));
		assertFalse("suffix should already exist", rule1.addRequiredSuffixCommand(new AddCommand(), false));
		
		
		
		assertTrue("added prefix was not unique", rule1.addRequiredPrefixCommand(new AddCommand(), true));
		assertTrue("added suffix was not unique", rule1.addRequiredSuffixCommand(new AddCommand(), true));
		
		assertFalse("prefix should already exist", rule1.addRequiredPrefixCommand(new AddCommand(), false));
		assertFalse("suffix should already exist", rule1.addRequiredSuffixCommand(new AddCommand(), false));
		
		assertFalse("prefix should already exist", rule1.addRequiredPrefixCommand(new AddCommand(), true));
		assertFalse("suffix should already exist", rule1.addRequiredSuffixCommand(new AddCommand(), true));
		
	}
	
	@Test
	public void testCheckingPrefixAndSuffix_AgainstOnlyProhibitedSamples() {
		
		MappingRule rule1 = new MappingRule("\\s+add\\s+", new AddCommand());
		
		assertTrue(rule1.checkIfPrefixCommandIsValid(new SubCommand()));
		assertTrue(rule1.checkIfPrefixCommandIsValid(new FactorialCommand()));
		
		rule1.addProhibitedPrefixCommand(new AddCommand(), true);
		
		assertTrue(rule1.checkIfPrefixCommandIsValid(new SubCommand()));
		assertTrue(rule1.checkIfPrefixCommandIsValid(new FactorialCommand()));
		
		rule1.addProhibitedPrefixCommand(new FactorialCommand(), false);
		
		assertTrue(rule1.checkIfPrefixCommandIsValid(new SubCommand()));
		assertFalse(rule1.checkIfPrefixCommandIsValid(new FactorialCommand()));
		
		rule1.addProhibitedPrefixCommand(new AddCommand(), false);
		
		assertFalse(rule1.checkIfPrefixCommandIsValid(new SubCommand()));
		assertFalse(rule1.checkIfPrefixCommandIsValid(new FactorialCommand()));
		
		
		
		assertTrue(rule1.checkIfSuffixCommandIsValid(new SubCommand()));
		assertTrue(rule1.checkIfSuffixCommandIsValid(new FactorialCommand()));
		
		rule1.addProhibitedSuffixCommand(new AddCommand(), true);
		
		assertTrue(rule1.checkIfSuffixCommandIsValid(new SubCommand()));
		assertTrue(rule1.checkIfSuffixCommandIsValid(new FactorialCommand()));
		
		rule1.addProhibitedSuffixCommand(new FactorialCommand(), false);
		
		assertTrue(rule1.checkIfSuffixCommandIsValid(new SubCommand()));
		assertFalse(rule1.checkIfSuffixCommandIsValid(new FactorialCommand()));
		
		rule1.addProhibitedSuffixCommand(new AddCommand(), false);
		
		assertFalse(rule1.checkIfSuffixCommandIsValid(new SubCommand()));
		assertFalse(rule1.checkIfSuffixCommandIsValid(new FactorialCommand()));
	}
	
	@Test
	public void testCheckingPrefixAndSuffix() {
		MappingRule rule1 = new MappingRule("\\s+add\\s+", new AddCommand());
		
		assertTrue(rule1.checkIfPrefixCommandIsValid(new SubCommand()));
		assertTrue(rule1.checkIfPrefixCommandIsValid(new FactorialCommand()));
		
		rule1.addRequiredPrefixCommand(new AddCommand(), false);
		
		assertTrue(rule1.checkIfPrefixCommandIsValid(new SubCommand()));
		assertFalse(rule1.checkIfPrefixCommandIsValid(new FactorialCommand()));
		
		rule1.addProhibitedPrefixCommand(new SubCommand(), true);
		
		assertFalse(rule1.checkIfPrefixCommandIsValid(new SubCommand()));
		assertFalse(rule1.checkIfPrefixCommandIsValid(new FactorialCommand()));
		
		rule1.addRequiredPrefixCommand(new FactorialCommand(), false);
		
		assertFalse(rule1.checkIfPrefixCommandIsValid(new SubCommand()));
		assertTrue(rule1.checkIfPrefixCommandIsValid(new FactorialCommand()));
	}
	
}
