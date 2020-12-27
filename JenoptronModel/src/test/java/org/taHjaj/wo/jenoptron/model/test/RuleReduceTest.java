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
package org.taHjaj.wo.jenoptron.model.test;

import org.taHjaj.wo.jenoptron.model.junit.AbstractTestCase;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import org.taHjaj.wo.jenoptron.model.core.DecisionTable;
import org.taHjaj.wo.jenoptron.model.core.binary.BinaryAction;
import org.taHjaj.wo.jenoptron.model.core.binary.BinaryActionValue;
import org.taHjaj.wo.jenoptron.model.core.binary.BinaryCondition;
import org.taHjaj.wo.jenoptron.model.core.binary.BinaryConditionValue;
import org.taHjaj.wo.jenoptron.model.icore.IAction;
import org.taHjaj.wo.jenoptron.model.icore.ICondition;
import org.taHjaj.wo.jenoptron.model.icore.IDecisionTable;
import org.taHjaj.wo.jenoptron.model.icore.IRule;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
