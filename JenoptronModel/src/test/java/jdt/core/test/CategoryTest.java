package jdt.core.test;

import org.apache.log4j.Logger;
import org.junit.Test;

import jdt.core.DecisionTable;
import jdt.core.binary.BinaryAction;
import jdt.core.binary.BinaryActionValue;
import jdt.core.binary.BinaryCondition;
import jdt.core.binary.BinaryConditionValue;
import jdt.core.category.ExclusiveConditionsGroup;
import jdt.core.category.IExclusiveConditionGroup;
import jdt.core.junit.AbstractTestCase;
import jdt.icore.IDecisionTable;
import jdt.icore.IRule;

public class CategoryTest extends AbstractTestCase {
	private static final Logger logger = Logger.getLogger(CategoryTest.class);

	@Test
	public void testSimple() {
		final IDecisionTable decisionTable = new DecisionTable();

		final IExclusiveConditionGroup categoryGroup = new ExclusiveConditionsGroup("Test")
				.add(new BinaryCondition("A")).add(new BinaryCondition("B"));

		decisionTable.add(categoryGroup);

		{
			final String string = dump(decisionTable);
			assertEquals("YN A\n" + "NY B\n", string);
			logger.debug("\n" + string);
		}

		decisionTable.reduce();

		{
			final String string = dump(decisionTable);
			assertEquals("- A\n" + "- B\n", string);
			logger.debug("\n" + string);
		}
	}

	@Test
	public void testSimple2() {
		final IDecisionTable decisionTable = new DecisionTable();

		final IExclusiveConditionGroup categoryGroup = new ExclusiveConditionsGroup("Test")
				.add(new BinaryCondition("A")).add(new BinaryCondition("B")).add(new BinaryCondition("C"));

		final IDecisionTable decisionTable2 = decisionTable.add(categoryGroup).add(new BinaryAction("abc"))
				.setActionValues(BinaryActionValue.DO).split();

		{
			final String string = dump(decisionTable2);
			assertEquals("YYYYNNNN A\n" + "YYNNYYNN B\n" + "YNYNYNYN C\n" + "XXXXXXXX abc\n", string);
			logger.debug(string);
		}

		final IDecisionTable decisionTable3 = decisionTable2.reduce();

		{
			final String string = dump(decisionTable3);
			assertEquals("- A\n" + "- B\n" + "- C\n" + "X abc\n", string);
			logger.debug(string);
		}
	}

	@Test
	public void testSimple3() {
		final IDecisionTable decisionTable = new DecisionTable();

		final IExclusiveConditionGroup categoryGroup = new ExclusiveConditionsGroup("Test")
				.add(new BinaryCondition("A")).add(new BinaryCondition("B")).add(new BinaryCondition("C"));

		final IDecisionTable decisionTable2 = decisionTable.add(categoryGroup).split();

		{
			final String string = dump(decisionTable2);
			assertEquals("YNN A\n" + "NYN B\n" + "NNY C\n", string);
			logger.debug(string);
		}

		decisionTable.reduce();
		decisionTable.split();

		{
			final String string = dump(decisionTable);
			assertEquals("YNN A\n" + "NYN B\n" + "NNY C\n", string);
			logger.debug(string);
		}
	}

	@Test
	public void testSimple4() {
		final IDecisionTable decisionTable = new DecisionTable();

		final IExclusiveConditionGroup categoryGroup = new ExclusiveConditionsGroup("Test")
				.add(new BinaryCondition("A")).add(new BinaryCondition("B"));

		decisionTable.add(categoryGroup);
		decisionTable.add(new BinaryCondition("C"));

		decisionTable.split();

		{
			final String string = dump(decisionTable);
			assertEquals("YYNN A\n" + "NNYY B\n" + "YNYN C\n", string);
			logger.debug("\n" + string);
		}

		decisionTable.reduce();
		decisionTable.split();
		decisionTable.reduce();

		{
			final String string = dump(decisionTable);
			assertEquals("- A\n" + "- B\n" + "- C\n", string);
			logger.debug("\n" + string);
		}

		decisionTable.split();

		{
			final String string = dump(decisionTable);
			assertEquals("YYNN A\n" + "NNYY B\n" + "YNYN C\n", string);
			logger.debug("\n" + string);
		}

		final BinaryAction abc = new BinaryAction("abc");
		decisionTable.add(abc);
		{
			final IRule rule = decisionTable.getRule(BinaryConditionValue.YES, BinaryConditionValue.YES,
					BinaryConditionValue.YES);
			assertNull(rule);
		}

		decisionTable.setActionValues(BinaryActionValue.DO);

		final IRule rule = decisionTable.getRule(BinaryConditionValue.YES, BinaryConditionValue.NO,
				BinaryConditionValue.YES);
		assertNotNull(rule);
		rule.setActionValue(abc, BinaryActionValue.DONT);

		{
			final String string = dump(decisionTable);

			logger.debug("\n" + string);
		}

		decisionTable.reduce();

		{
			final String string = dump(decisionTable);

			logger.debug("\n" + string);
		}
	}

	@Test
	public void testRules() {
		final IDecisionTable decisionTable = new DecisionTable();

		final BinaryCondition binaryConditionA = new BinaryCondition("A");
		final BinaryCondition binaryConditionB = new BinaryCondition("B");
		final IExclusiveConditionGroup categoryGroup = new ExclusiveConditionsGroup("Test").add(binaryConditionA)
				.add(binaryConditionB);

		assertTrue(categoryGroup.conditions().contains(binaryConditionA));
		assertTrue(categoryGroup.conditions().contains(binaryConditionB));

		final IDecisionTable decisionTable2 = decisionTable.add(categoryGroup);

		final IRule rule = decisionTable2.getRule(BinaryConditionValue.YES, BinaryConditionValue.YES);
		assertNull(rule);
	}
}
