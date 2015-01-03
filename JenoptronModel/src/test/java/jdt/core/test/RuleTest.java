package jdt.core.test;

import jdt.core.Rule;
import jdt.core.binary.BinaryAction;
import jdt.core.binary.BinaryCondition;
import jdt.core.binary.BinaryConditionValue;
import jdt.core.junit.AbstractTestCase;
import jdt.icore.ICondition;
import jdt.icore.IRule;

import org.apache.log4j.Logger;
import org.junit.Test;

public class RuleTest extends AbstractTestCase {
	private static Logger logger = Logger.getLogger( RuleTest.class);
	
	@Test
	public void testCompare() {
		final IRule rule;
		final IRule rule2;
		
		{
			rule = new Rule();
		}
		
		{
			rule2 = new Rule();
		}
		
		assertTrue( rule.equals( rule2));
		
		logger.debug( rule);
	}
	
	@Test
	public void testCompare2() {
		final IRule rule;
		final IRule rule2;
		
		{
			rule = new Rule();
			rule.addCondition( new BinaryCondition());
		}
		
		{
			rule2 = new Rule();
		}
		
		assertFalse( rule.equals( rule2));
		
		logger.debug( rule);
	}
	
	@Test
	public void testCompare3() {
		final IRule rule;
		final IRule rule2;
		
		{
			rule = new Rule();
			rule.addAction( new BinaryAction());
		}
		
		{
			rule2 = new Rule();
		}
		
		assertFalse( rule.equals( rule2));
		
		logger.debug( rule);
	}
	
	@Test
	public void testCompare4() {
		final IRule rule;
		final IRule rule2;
		
		{
			rule = new Rule();
			rule.addAction( new BinaryAction());
		}
		
		{
			rule2 = new Rule();
			rule2.addCondition( new BinaryCondition());
		}
		
		assertFalse( rule.equals( rule2));
		
		logger.debug( rule);
	}
	
	@Test
	public void testCompare5() {
		final IRule rule;
		final IRule rule2;
		
		final ICondition condition = new BinaryCondition();
		
		{
			rule = new Rule();
			rule.addCondition( condition);
		}
		
		{
			rule2 = new Rule();
			rule2.addCondition( condition);
		}
		
		assertTrue( rule.equals( rule2));
		
		logger.debug( rule);
	}
	
	@Test
	public void testCompare6() {
		final IRule rule;
		final IRule rule2;
		
		final ICondition condition = new BinaryCondition();
		
		{
			rule = new Rule();
			rule.addCondition( condition);
			rule.setConditionValue( condition, BinaryConditionValue.NO);
		}
		
		{
			rule2 = new Rule();
			rule2.addCondition( condition);
			rule2.setConditionValue( condition, BinaryConditionValue.NO);
		}
		
		assertTrue( rule.equals( rule2));
		
		logger.debug( rule);
	}
	
	@Test
	public void testCompare7() {
		final IRule rule;
		final IRule rule2;
		
		final ICondition condition = new BinaryCondition();
		final ICondition condition2 = new BinaryCondition();
		
		{
			rule = new Rule();
			rule.addCondition( condition);
			rule.addCondition( condition2);
			rule.setConditionValue( condition, BinaryConditionValue.NO);
			rule.setConditionValue( condition2, BinaryConditionValue.YES);
		}
		
		{
			rule2 = new Rule();
			rule2.addCondition( condition);
			rule2.addCondition( condition2);
			rule2.setConditionValue( condition, BinaryConditionValue.NO);
			rule2.setConditionValue( condition2, BinaryConditionValue.YES);
		}
		
		assertTrue( rule.equals( rule2));
		
		logger.debug( rule);
	}
}
