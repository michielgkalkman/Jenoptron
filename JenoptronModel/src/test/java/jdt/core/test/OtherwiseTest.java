package jdt.core.test;

import static jdt.core.binary.BinaryActionValue.DO;
import static jdt.core.binary.BinaryActionValue.DONT;
import static jdt.core.binary.BinaryConditionValue.NO;
import static jdt.core.binary.BinaryConditionValue.YES;

import org.apache.log4j.Logger;
import org.junit.Test;

import jdt.core.DecisionTable;
import jdt.core.junit.AbstractTestCase;
import jdt.icore.IDecisionTable;

public class OtherwiseTest extends AbstractTestCase {
	private static final Logger logger = Logger.getLogger(OtherwiseTest.class);

	@Test
	public void testFluentInterface() {
		final IDecisionTable decisionTable = new DecisionTable();

		decisionTable.setDefaultActionValue(DONT).addBinaryActions("action1", "action2")
				.addBinaryConditions("condition1", "condition2");

		decisionTable.split();

		{
			final String string = dump(decisionTable);
			logger.debug('\n' + string);
			assertEquals("YYNN condition1\n" + "YNYN condition2\n" + "???? action1\n" + "???? action2\n", string);
		}

		decisionTable.setActionValues(decisionTable.getRule(YES, NO), DO, DONT)
				.setActionValues(decisionTable.getRule(NO, YES), DONT, DONT);

		{
			final String string = dump(decisionTable);
			logger.debug('\n' + string);
			assertEquals("YYNN condition1\n" + "YNYN condition2\n" + "?X ? action1\n" + "?  ? action2\n", string);
		}
	}

	@Test
	public void testDefaultValue() {
		final IDecisionTable decisionTable = new DecisionTable(DO);

		decisionTable.addBinaryConditions("condition1", "condition2").addBinaryActions("action1", "action2").split();

		{
			final String string = dump(decisionTable);
			logger.debug('\n' + string);
			assertEquals("YYNN condition1\n" + "YNYN condition2\n" + "XXXX action1\n" + "XXXX action2\n", string);
		}

		decisionTable.setActionValues(decisionTable.getRule(YES, NO), DO, DONT)
				.setActionValues(decisionTable.getRule(NO, YES), DONT, DONT);

		{
			final String string = dump(decisionTable);
			logger.debug('\n' + string);
			assertEquals("YYNN condition1\n" + "YNYN condition2\n" + "XX X action1\n" + "X  X action2\n", string);
		}

	}
}
