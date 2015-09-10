package jdt.core.test;

import org.apache.log4j.Logger;
import org.junit.Test;

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

public class RemoveConditionTest extends AbstractTestCase {
	private static final Logger logger = Logger.getLogger(RemoveConditionTest.class);

	@Test
	public void testSimple() {
		final ICondition condition = new BinaryCondition();
		final IAction action = new BinaryAction();

		final IDecisionTable decisionTable = new DecisionTable().add(condition).add(action).split();

		for (final IRule rule : decisionTable.getRules()) {
			if (BinaryConditionValue.YES.equals(rule.getConditionValue(condition))) {
				rule.setActionValue(action, BinaryActionValue.DO);
			} else {
				rule.setActionValue(action, BinaryActionValue.DONT);
			}
		}

		logger.debug(dump(decisionTable));

		decisionTable.remove(condition);

		logger.debug(dump(decisionTable));

	}

	@Test
	public void testSimple3() {
		final ICondition condition = new BinaryCondition();
		final ICondition condition2 = new BinaryCondition("bc2");
		final IAction action = new BinaryAction();
		final IDecisionTable decisionTable = new DecisionTable().add(condition).add(condition2).add(action).split();

		for (final IRule rule : decisionTable.getRules()) {
			if (BinaryConditionValue.YES.equals(rule.getConditionValue(condition))) {
				rule.setActionValue(action, BinaryActionValue.DO);
			} else {
				rule.setActionValue(action, BinaryActionValue.DONT);
			}
		}

		logger.debug(dump(decisionTable));

		decisionTable.remove(condition2);

		logger.debug(dump(decisionTable));

	}

	@Test
	public void testSimple2() {
		final IAction action = new BinaryAction();
		final ICondition condition = new BinaryCondition();
		final ICondition condition2 = new BinaryCondition("bc2");

		final IDecisionTable decisionTable = new DecisionTable().add(condition).add(condition2).add(action).split();

		for (final IRule rule : decisionTable.getRules()) {
			if (((BinaryConditionValue.YES.equals(rule.getConditionValue(condition))
					&& BinaryConditionValue.YES.equals(rule.getConditionValue(condition2)))
					|| BinaryConditionValue.NO.equals(rule.getConditionValue(condition)))) {
				rule.setActionValue(action, BinaryActionValue.DO);
			} else {
				rule.setActionValue(action, BinaryActionValue.DONT);
			}
		}

		logger.debug(dump(decisionTable));

		decisionTable.remove(condition);

		logger.debug(dump(decisionTable));

		assertEquals(decisionTable.getAllRules().size(), 2);

		for (final IRule rule : decisionTable.getRules()) {
			if (rule.getConditionValue(condition2).equals(BinaryConditionValue.NO)) {
				assertEquals(rule.getActionValue(action), BinaryActionValue.UNKNOWN);
			}
			if (rule.getConditionValue(condition2).equals(BinaryConditionValue.YES)) {
				assertEquals(rule.getActionValue(action), BinaryActionValue.DO);
			}
		}

	}

}
