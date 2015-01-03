package jdt.core.test;

import jdt.core.DecisionTable;
import jdt.core.binary.BinaryCondition;
import jdt.core.junit.AbstractTestCase;
import jdt.icore.ICondition;
import jdt.icore.IDecisionTable;

import org.junit.Test;

public class DoubleConditionTest extends AbstractTestCase {
	
	@Test
	public void testSimple() {
		final IDecisionTable decisionTable  = new DecisionTable();

		final ICondition condition  = new BinaryCondition();

		try {
			decisionTable.add( condition);
			decisionTable.add( condition);
			fail();
		} catch (final RuntimeException e) {
		}
	}
}
