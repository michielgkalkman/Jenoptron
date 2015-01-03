package jdt.core.test;

import org.junit.Test;

import jdt.core.DecisionTable;
import jdt.core.binary.BinaryAction;
import jdt.core.binary.BinaryActionValue;
import jdt.core.junit.AbstractTestCase;
import jdt.icore.IAction;
import jdt.icore.IDecisionTable;

public class ActionTest extends AbstractTestCase {
	
	@Test
	public void testParseCondition() {
		final IDecisionTable decisionTable = new DecisionTable();
		
		// Add action
		final IAction action;
		{
			action = new BinaryAction();
			decisionTable.add( action);
		}

		assertEquals( BinaryActionValue.UNKNOWN, action.parse( "?"));
		assertEquals( BinaryActionValue.UNKNOWN, action.parse( "WW"));
	}
}
