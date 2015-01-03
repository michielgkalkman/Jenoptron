package jdt.core.test;

import jdt.core.DecisionTable;
import jdt.core.binary.BinaryCondition;
import jdt.core.junit.AbstractTestCase;
import jdt.icore.ICondition;
import jdt.icore.IDecisionTable;

import org.junit.Test;

public class PropertyChangeTest extends AbstractTestCase {

	@Test
	public void testSimple() {
		final IDecisionTable decisionTable = new DecisionTable();

		// Add condition
		ICondition condition;
		{
			condition = new BinaryCondition();
			decisionTable.add(condition);
		}

		condition.setShortDescription("x");
	}

	@Test
	public void testSimple2() {
		final IDecisionTable decisionTable = new DecisionTable();

		// Add condition
		ICondition condition;
		{
			condition = new BinaryCondition();
			decisionTable.add(condition);
		}
		decisionTable.setShortDescription("x");
	}
}
