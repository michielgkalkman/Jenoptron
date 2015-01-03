package jdt.core.test;

import jdt.core.DecisionTable;
import jdt.core.binary.BinaryAction;
import jdt.core.binary.BinaryCondition;
import jdt.core.junit.AbstractTestCase;
import jdt.icore.IAction;
import jdt.icore.ICondition;
import jdt.icore.IDecisionTable;

import org.apache.log4j.Logger;

public class RemoveActionTest extends AbstractTestCase {
	private static final Logger logger = Logger.getLogger( RemoveActionTest.class);

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

		// Add action
		IAction action;
		{
			action = new BinaryAction();
			decisionTable.add( action);
		}

		decisionTable.remove( action);

		logger.debug( dump( decisionTable));
	}

	public void testSimple2() {
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

		// Add action
		IAction action;
		{
			action = new BinaryAction();
			decisionTable.add( action);
		}

		decisionTable.reduce();
		final String before = dump( decisionTable);

		decisionTable.remove( action);
		decisionTable.add( action);

		final String after = dump( decisionTable);

		assertEquals( before, after);
	}
}
