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
import org.taHjaj.wo.jenoptron.model.icore.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.taHjaj.wo.jenoptron.model.core.binary.BinaryActionValue.DO;
import static org.taHjaj.wo.jenoptron.model.core.binary.BinaryActionValue.DONT;
import static org.taHjaj.wo.jenoptron.model.core.binary.BinaryConditionValue.NO;
import static org.taHjaj.wo.jenoptron.model.core.binary.BinaryConditionValue.YES;

public class SubtableTest extends AbstractTestCase {
	private static final Logger logger = Logger.getLogger(PropertyChangeTest.class);

	@Test
	public void testGetConditionalRules() {
		final ICondition condition1 = new BinaryCondition("bin1");
		final ICondition condition2 = new BinaryCondition("bin2");
		final ICondition condition3 = new BinaryCondition("bin3");

		final IDecisionTable decisionTable = new DecisionTable().add(condition1).add(condition2).add(condition3);

		decisionTable.split();

		final Map<ICondition, IConditionValue> conditions = decisionTable.getRule(1).getConditions();

		conditions.remove(condition1);

		final List<IRule> interestingRules = decisionTable.getRules(conditions);

		logger.debug(interestingRules);
	}

	@Test
	public void testSimple() {
		final IAction action = new BinaryAction("ac2");

		final BinaryCondition bin1 = new BinaryCondition("bin1");
		final IDecisionTable decisionTable = new DecisionTable().add(bin1).add(new BinaryAction("ac1")).add(action)
				.split();
		decisionTable.getRule(1).setActionValue(action, BinaryActionValue.DO);

		final IDecisionTable decisionTable2 = decisionTable.getSubtable(Collections.singletonList(bin1), Collections.singletonList(action));

		decisionTable2.split();

		logger.debug(dump(decisionTable));
		logger.debug(dump(decisionTable2));

		{
			final String string = dump(decisionTable);
			logger.debug('\n' + string);
			assertEquals("YN bin1\n" + "?? ac1\n" + "?X ac2\n", string);
		}

		{
			final String string = dump(decisionTable2);

			assertEquals("YN bin1\n" + "?X ac2\n", string);
		}
	}

	@Test
	public void testSimple2() {
		final IDecisionTable addBinaryActions = new DecisionTable().setDefaultActionValue(DONT)
				.addBinaryActions("action1", "action2");

		assertEquals(
				"\" action1\\n\" +\n\" action2\\n\" +\nSome Decision Table\nRules:\n\nConditions:\n\nActions:\naction1\naction2\n",
				addBinaryActions.toString());

		final IDecisionTable addBinaryConditions = addBinaryActions.addBinaryConditions("condition1", "condition2",
				"condition3");
		final IDecisionTable decisionTable = addBinaryConditions.split();

		final IRule rule = decisionTable.getRule(YES, NO, NO);
		final IDecisionTable decisionTable1a = decisionTable.setActionValues(rule, DO, DONT);
		final IDecisionTable decisionTable1b = decisionTable1a.setActionValues(decisionTable1a.getRule(NO, NO, NO),
				DONT, DONT);
		{
			final String string = dump(decisionTable1b);
			logger.debug('\n' + string);
			assertEquals("YYYYNNNN condition1\n" + "YYNNYYNN condition2\n" + "YNYNYNYN condition3\n"
					+ "???X???  action1\n" + "??? ???  action2\n", string);
		}

		assertTrue(decisionTable1b.validate());

		final IDecisionTable decisionTable2 = decisionTable1b.getSubtable("condition2", "condition3");

		{
			final String string = dump(decisionTable);
			logger.debug('\n' + string);
			assertEquals("YYYYNNNN condition1\n" + "YYNNYYNN condition2\n" + "YNYNYNYN condition3\n"
					+ "???X???  action1\n" + "??? ???  action2\n", string);
		}

		{
			final String string = dump(decisionTable2);
			logger.debug('\n' + string);
			assertEquals("YYNN condition2\n" + "YNYN condition3\n" + "???? action1\n" + "???  action2\n", string);
		}
	}
}
