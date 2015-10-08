/*******************************************************************************
 * Copyright 2015 Michiel Kalkman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package jdt.core.test;

import org.apache.log4j.Logger;
import org.junit.Test;

import jdt.core.DecisionTable;
import jdt.core.binary.BinaryAction;
import jdt.core.binary.BinaryActionValue;
import jdt.core.binary.BinaryCondition;
import jdt.core.junit.AbstractTestCase;
import jdt.icore.IAction;
import jdt.icore.ICondition;
import jdt.icore.IDecisionTable;
import jdt.icore.IRule;

public class UpDownActionTest extends AbstractTestCase {
	private static final Logger logger = Logger.getLogger(UpDownActionTest.class);

	@Test
	public void testSimple() {
		final IDecisionTable decisionTable = new DecisionTable();

		// Add condition
		{
			final ICondition condition = new BinaryCondition();
			decisionTable.add(condition);
		}

		// Add action
		{
			final IAction action = new BinaryAction();
			decisionTable.add(action);
		}

		// Add action
		IAction action;
		{
			action = new BinaryAction();
			decisionTable.add(action);
		}

		final IDecisionTable decisionTable2 = decisionTable.up(action).down(action);

		assertEquals(decisionTable, decisionTable2);

		logger.debug(dump(decisionTable));
		logger.debug(dump(decisionTable2));
	}

	@Test
	public void testSimple2() {
		final IDecisionTable decisionTable = new DecisionTable();

		// Add condition
		{
			final ICondition condition = new BinaryCondition();
			decisionTable.add(condition);
		}

		// Add condition
		ICondition condition;
		{
			condition = new BinaryCondition();
			decisionTable.add(condition);
		}

		// Add action
		{
			final IAction action = new BinaryAction();
			decisionTable.add(action);
		}

		final IDecisionTable decisionTable2 = decisionTable.up(condition).down(condition);

		decisionTable.reduce();
		decisionTable2.reduce();

		logger.debug(dump(decisionTable));
		logger.debug(dump(decisionTable2));

		assertEquals(decisionTable, decisionTable2);
	}

	public void testSimple3() {
		logger.debug("testSimple3()");

		final ICondition condition2 = new BinaryCondition("c2");
		final IAction action = new BinaryAction();

		final IDecisionTable decisionTable = new DecisionTable().add(new BinaryCondition("c1")).add(condition2)
				.add(action).split();

		assertTrue(decisionTable.validate());

		{
			final IRule rule = decisionTable.getRule(0);
			rule.setActionValue(action, BinaryActionValue.DO);
		}
		{
			final IRule rule = decisionTable.getRule(1);
			rule.setActionValue(action, BinaryActionValue.DO);
		}
		{
			final IRule rule = decisionTable.getRule(2);
			rule.setActionValue(action, BinaryActionValue.DONT);
		}
		{
			final IRule rule = decisionTable.getRule(3);
			rule.setActionValue(action, BinaryActionValue.DO);
		}
		{
			final String string = dump(decisionTable);
			logger.debug(string);
			assertEquals("YYNN c1\n" + "YNYN c2\n" + "XX X BinaryAction\n", string);
		}

		final IDecisionTable decisionTable2 = decisionTable.up(condition2);

		{
			final String string = dump(decisionTable2);
			logger.debug(string);
			assertEquals( //
					"YYNN c2\n" + //
							"YNYN c1\n" + //
							"X XX BinaryAction\n",
					string);
		}

		final IDecisionTable decisionTable3 = decisionTable2.down(condition2);
		{
			final String string = dump(decisionTable3);
			logger.debug(string);
			assertEquals( //
					"YYNN c1\n" + //
							"YNYN c2\n" + //
							"XX X BinaryAction\n",
					string);
		}

		final IDecisionTable decisionTable4 = decisionTable3.reduce();
		{
			final String string = dump(decisionTable4);
			logger.debug(string);
			assertEquals("YNN c1\n" + "-YN c2\n" + "X X BinaryAction\n", string);
		}

		final IDecisionTable decisionTable5 = decisionTable4.up(condition2);

		{
			final String string = dump(decisionTable5);
			logger.debug(string);
			assertEquals("YN- c2\n" + "NNY c1\n" + " XX BinaryAction\n", string);
		}
	}

	@Test
	public void testSimpleWithList() {
		final BinaryAction binaryAction = new BinaryAction("1");

		final IDecisionTable decisionTable = new DecisionTable().add(getConditionList("a", "b", "c"));
		final IDecisionTable decisionTable2 = decisionTable.add(binaryAction).split()
				.setActionValues(BinaryActionValue.DO).split();

		// Add condition
		{
			decisionTable2.getRule(0).setActionValue(binaryAction, BinaryActionValue.DONT);

			{
				final String string = dump(decisionTable2);
				logger.debug(string);
				assertEquals("YYYYNNNN a\n" + "YYNNYYNN b\n" + "YNYNYNYN c\n" + " XXXXXXX 1\n", string);
			}

			final IDecisionTable decisionTable3 = decisionTable2.reduce();

			{
				final String string = dump(decisionTable3);
				logger.debug(string);
				assertEquals("YN-- a\n" + "Y-N- b\n" + "Y--N c\n" + " XXX 1\n", string);
			}
		}
	}

	@Test
	public void testSimpleWithList2() {
		final BinaryAction binaryAction = new BinaryAction("1");
		final IDecisionTable decisionTable = new DecisionTable().add(getConditionList("a", "b")).add(binaryAction)
				.split().setActionValues(BinaryActionValue.DO).split();

		// Add condition
		{

			decisionTable.getRule(1).setActionValue(binaryAction, BinaryActionValue.DONT);

			{
				final String string = dump(decisionTable);
				logger.debug(string);
				assertEquals("YYNN a\n" + "YNYN b\n" + "X XX 1\n", string);
			}

			final IDecisionTable decisionTable2 = decisionTable.reduce().split();

			{
				final String string = dump(decisionTable2);
				logger.debug(string);
				assertEquals("YYNN a\n" + "YNYN b\n" + "X XX 1\n", string);
			}
		}
	}
}
