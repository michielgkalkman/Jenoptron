package jdt.core.test;

import jdt.core.DecisionTable;
import jdt.core.binary.BinaryAction;
import jdt.core.binary.BinaryActionValue;
import jdt.core.binary.BinaryCondition;
import jdt.core.junit.AbstractTestCase;
import jdt.icore.IAction;
import jdt.icore.ICondition;
import jdt.icore.IDecisionTable;
import jdt.icore.IRule;

import org.apache.log4j.Logger;

public class ResetActionTest extends AbstractTestCase {
	private static Logger logger = Logger.getLogger( ResetActionTest.class);

	public void testResetAction() {
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

		{
			final String string = dump( decisionTable);
			logger.debug( string);
			assertEquals( "YN BinaryCondition\n" +
					"?? BinaryAction\n", string);
		}

		{
			final IRule rule = decisionTable.getRule(0);
			rule.setActionValue( action, BinaryActionValue.DO);

			final String string = dump( decisionTable);
			logger.debug( string);
			assertEquals( "YN BinaryCondition\n" +
					"X? BinaryAction\n", string);
		}


		{
			final IRule rule = decisionTable.getRule(0);
			rule.setActionValue( action, BinaryActionValue.UNKNOWN);

			final String string = dump( decisionTable);
			logger.debug( string);
			assertEquals( "YN BinaryCondition\n" +
					"?? BinaryAction\n", string);
		}
	}

}
