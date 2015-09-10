package jdt.core.test;

import org.junit.Test;

import jdt.core.DecisionTable;
import jdt.core.binary.BinaryCondition;
import jdt.core.junit.AbstractTestCase;
import jdt.icore.ICondition;
import jdt.icore.IDecisionTable;

public class DoubleConditionTest extends AbstractTestCase {

	@Test
	public void testSimple() {
		final ICondition condition = new BinaryCondition();

		try {
			final IDecisionTable decisionTable = new DecisionTable().add(condition).add(condition);
			fail();
		} catch (final RuntimeException e) {
		}
	}
}
