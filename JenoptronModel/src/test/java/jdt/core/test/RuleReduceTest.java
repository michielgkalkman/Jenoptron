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

public class RuleReduceTest extends AbstractTestCase {
	private static final Logger logger = Logger.getLogger(RuleReduceTest.class);

	@Test
	public void testSimple() {
		logger.debug("testSimple()");

		final IDecisionTable decisionTable = new DecisionTable().add(new BinaryCondition()).add(new BinaryAction());

		{
			final String string = dump(decisionTable);
			logger.debug(string);
			assertEquals("YN BinaryCondition\n" + "?? BinaryAction\n", string);
		}

		final IDecisionTable decisionTable2 = decisionTable.reduce();

		{
			final String string = dump(decisionTable2);
			logger.debug(string);
			assertEquals("- BinaryCondition\n" + "? BinaryAction\n", string);
		}

		final IDecisionTable decisionTable3 = decisionTable2.split();

		{
			final String string = dump(decisionTable3);
			logger.debug(string);
			assertEquals("YN BinaryCondition\n" + "?? BinaryAction\n", string);
		}

		final IDecisionTable decisionTable4 = decisionTable3.reduce();

		{
			final String string = dump(decisionTable4);
			logger.debug(string);
			assertEquals("- BinaryCondition\n" + "? BinaryAction\n", string);
		}
		logger.debug("testSimple()");
	}

	@Test
	public void testSimple1() {
		logger.debug("testSimple1()");

		final IAction action = new BinaryAction();
		final IDecisionTable decisionTable = new DecisionTable().add(new BinaryCondition()).add(action).split();

		final IRule rule = decisionTable.getRule(0);
		rule.setActionValue(action, BinaryActionValue.DO);

		{
			final String string = dump(decisionTable);
			logger.debug(string);
			assertEquals("YN BinaryCondition\n" + "X? BinaryAction\n", string);
		}

		final IDecisionTable split = decisionTable.split();

		{
			final String string = dump(split);
			logger.debug(string);
			assertEquals("YN BinaryCondition\n" + "X? BinaryAction\n", string);
		}

		final IDecisionTable reduce = split.reduce();

		{
			final String string = dump(reduce);
			logger.debug(string);
			assertEquals("YN BinaryCondition\n" + "X? BinaryAction\n", string);
		}

		// Add condition
		final ICondition condition = new BinaryCondition();
		final IDecisionTable decisionTable2 = reduce.add(condition);

		{
			final String string = dump(decisionTable2);
			logger.debug(string);
			assertEquals("YN BinaryCondition\n" + "-- BinaryCondition\n" + "X? BinaryAction\n", string);
		}

		logger.debug("testSimple1()");
	}

	@Test
	public void testSimple4() {
		// Add condition
		final ICondition condition = new BinaryCondition();
		final ICondition condition2 = new BinaryCondition("bc2");
		final IAction action = new BinaryAction();

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

		final IDecisionTable decisionTable2 = decisionTable.reduce();

		logger.debug(dump(decisionTable2));

		assertEquals(decisionTable2.getRules().size(), 3);
	}

}
