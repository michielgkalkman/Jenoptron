package jdt.core.test;

import jdt.core.DecisionTable;
import jdt.core.binary.BinaryAction;
import jdt.core.binary.BinaryActionValue;
import jdt.core.binary.BinaryCondition;
import jdt.core.binary.BinaryConditionValue;
import jdt.core.junit.AbstractTestCase;
import jdt.icore.IAction;
import jdt.icore.ICondition;
import jdt.icore.IDecisionTable;
import jdt.icore.IRule;

import org.apache.log4j.Logger;

public class RuleReduceTest extends AbstractTestCase {
	private static final Logger logger = Logger.getLogger( RuleReduceTest.class);

	public void testSimple() {
		logger.debug( "testSimple()");

		final IDecisionTable decisionTable = new DecisionTable();

		// Add condition
		{
			final ICondition condition = new BinaryCondition();
			decisionTable.add( condition);
		}

		// Add action
		{
			final IAction action = new BinaryAction();
			decisionTable.add( action);
		}

		{
			final String string = dump( decisionTable);
			logger.debug( string);
			assertEquals( "YN BinaryCondition\n" +
					"?? BinaryAction\n", string);
		}

		decisionTable.reduce();

		{
			final String string = dump( decisionTable);
			logger.debug( string);
			assertEquals( "- BinaryCondition\n" +
					"? BinaryAction\n", string);
		}

		decisionTable.split();

		{
			final String string = dump( decisionTable);
			logger.debug( string);
			assertEquals( "YN BinaryCondition\n" +
					"?? BinaryAction\n", string);
		}

		decisionTable.reduce();

		{
			final String string = dump( decisionTable);
			logger.debug( string);
			assertEquals( "- BinaryCondition\n" +
					"? BinaryAction\n", string);
		}
		logger.debug( "testSimple()");
	}

	public void testSimple1() {
		logger.debug( "testSimple1()");

		final IDecisionTable decisionTable = new DecisionTable();

		// Add condition
		{
			final ICondition condition = new BinaryCondition();
			decisionTable.add( condition);
		}

		// Add action
		final IAction action;
		{
			action = new BinaryAction();
			decisionTable.add( action);
		}

		decisionTable.split();

		final IRule rule = decisionTable.getRule(0);
		rule.setActionValue( action, BinaryActionValue.DO);

		{
			final String string = dump( decisionTable);
			logger.debug( string);
			assertEquals( "YN BinaryCondition\n" +
					"X? BinaryAction\n", string);
		}

		decisionTable.split();

		{
			final String string = dump( decisionTable);
			logger.debug( string);
			assertEquals( "YN BinaryCondition\n" +
					"X? BinaryAction\n", string);
		}

		decisionTable.reduce();

		{
			final String string = dump( decisionTable);
			logger.debug( string);
			assertEquals( "YN BinaryCondition\n" +
					"X? BinaryAction\n", string);
		}

		// Add condition
		{
			final ICondition condition = new BinaryCondition();
			decisionTable.add( condition);
		}

		{
			final String string = dump( decisionTable);
			logger.debug( string);
			assertEquals( "YN BinaryCondition\n" +
					"-- BinaryCondition\n" +
					"X? BinaryAction\n", string);
		}

		logger.debug( "testSimple1()");
	}


	public void testSimple4() {
		final IDecisionTable decisionTable = new DecisionTable();

		// Add condition
		ICondition condition;
		{
			condition = new BinaryCondition();
			decisionTable.add( condition);
		}

		// Add condition
		ICondition condition2;
		{
			condition2 = new BinaryCondition( "bc2");
			decisionTable.add( condition2);
		}

		// Add action
		IAction action;
		{
			action = new BinaryAction();
			decisionTable.add( action);
		}

		decisionTable.split();

		for( final IRule rule : decisionTable.getRules()) {
			if( ((BinaryConditionValue.YES.equals( rule.getConditionValue( condition))
						&& BinaryConditionValue.YES.equals( rule.getConditionValue( condition2)))
						|| BinaryConditionValue.NO.equals( rule.getConditionValue( condition)))) {
				rule.setActionValue( action, BinaryActionValue.DO);
			} else {
				rule.setActionValue( action, BinaryActionValue.DONT);
			}
		}

		logger.debug( dump( decisionTable));

		decisionTable.reduce();

		logger.debug( dump( decisionTable));

		assertEquals( decisionTable.getRules().size(), 3);
	}

}
