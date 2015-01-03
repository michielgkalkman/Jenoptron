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
import org.junit.Test;

public class RuleDeepcopyTest extends AbstractTestCase {
	private static final Logger logger = Logger.getLogger( RuleDeepcopyTest.class);

	@Test
	public void testSimple() {
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

		decisionTable.reduce();

		final IDecisionTable decisionTable2 = decisionTable.deepcopy();

		decisionTable2.reduce();

		logger.debug( dump( decisionTable));

		logger.debug( dump( decisionTable2));

		assertEquals( decisionTable, decisionTable2);

	}

	@Test
	public void testSimple2() {
		final IDecisionTable decisionTable = new DecisionTable();

		// Add condition
		{
			final ICondition condition = new BinaryCondition();
			decisionTable.add( condition);
		}

		// Add condition
		{
			final ICondition condition = new BinaryCondition();
			decisionTable.add( condition);
		}

		// Add condition
		{
			final ICondition condition = new BinaryCondition();
			decisionTable.add( condition);
		}

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

		// Add action
		{
			final IAction action = new BinaryAction();
			decisionTable.add( action);
		}

		// Add action
		{
			final IAction action = new BinaryAction();
			decisionTable.add( action);
		}

		// Add action
		{
			final IAction action = new BinaryAction();
			decisionTable.add( action);
		}

		decisionTable.reduce();

		final IDecisionTable decisionTable2 = decisionTable.deepcopy();

		decisionTable2.reduce();

		logger.debug( dump( decisionTable));

		logger.debug( dump( decisionTable2));

		assertEquals( decisionTable, decisionTable2);

	}

	@Test
	public void testSimple3() {
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

		final IDecisionTable decisionTableClone = decisionTable.deepcopy();

		logger.debug( dump( decisionTableClone));

		assertEquals( decisionTableClone.getAllRules().size(), 4);

		for( final IRule rule : decisionTable.getRules()) {
			if( ((BinaryConditionValue.YES.equals( rule.getConditionValue( condition))
						&& BinaryConditionValue.YES.equals( rule.getConditionValue( condition2)))
						|| BinaryConditionValue.NO.equals( rule.getConditionValue( condition)))) {
				assertEquals( rule.getActionValue( action), BinaryActionValue.DO);
			} else {
				assertEquals( rule.getActionValue( action), BinaryActionValue.DONT);
			}
		}
	}

	@Test
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

		final IDecisionTable decisionTableClone = decisionTable.deepcopy();

		logger.debug( dump( decisionTableClone));

		assertEquals( decisionTableClone.getAllRules().size(), 4);

		for( final IRule rule : decisionTable.getRules()) {
			if( ((BinaryConditionValue.YES.equals( rule.getConditionValue( condition))
						&& BinaryConditionValue.YES.equals( rule.getConditionValue( condition2)))
						|| BinaryConditionValue.NO.equals( rule.getConditionValue( condition)))) {
				assertEquals( rule.getActionValue( action), BinaryActionValue.DO);
			} else {
				assertEquals( rule.getActionValue( action), BinaryActionValue.DONT);
			}
		}

		decisionTable.split();
		decisionTableClone.split();

		assertEquals( decisionTable.equals( decisionTableClone), true);
	}
}
