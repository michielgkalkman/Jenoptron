package jdt.core.test;

import jdt.core.DecisionTable;
import jdt.core.binary.BinaryAction;
import jdt.core.binary.BinaryActionValue;
import jdt.core.binary.BinaryCondition;
import jdt.core.junit.AbstractTestCase;
import jdt.icore.IAction;
import jdt.icore.ICondition;
import jdt.icore.IDecisionTable;

import org.apache.log4j.Logger;

public class SubtableTest extends AbstractTestCase {
	private static final Logger logger = Logger.getLogger( PropertyChangeTest.class);

	public void testSimple() {
		final IDecisionTable decisionTable = new DecisionTable();

		// Add condition
		{
			final ICondition condition = new BinaryCondition( "bin1");
			decisionTable.add( condition);
		}

		// Add action
		{
			final IAction action = new BinaryAction( "ac1");
			decisionTable.add( action);
		}

		// Add action
		IAction action;
		{
			action = new BinaryAction( "ac2");
			decisionTable.add( action);
		}

		decisionTable.split();
		decisionTable.getRule(1).setActionValue( action, BinaryActionValue.DO);

		final int[] conditionRows = {0};
		final int[] actionRows = {1};

		final IDecisionTable decisionTable2 = decisionTable.getSubtable( conditionRows, actionRows);

		decisionTable2.split();

		logger.debug( dump( decisionTable));
		logger.debug( dump( decisionTable2));
	}
}
