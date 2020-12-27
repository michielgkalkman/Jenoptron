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
import org.junit.jupiter.api.Test;
import org.taHjaj.wo.jenoptron.model.core.DecisionTable;
import org.taHjaj.wo.jenoptron.model.core.binary.BinaryAction;
import org.taHjaj.wo.jenoptron.model.core.binary.BinaryActionValue;
import org.taHjaj.wo.jenoptron.model.core.binary.BinaryCondition;
import org.taHjaj.wo.jenoptron.model.icore.IAction;
import org.taHjaj.wo.jenoptron.model.icore.ICondition;
import org.taHjaj.wo.jenoptron.model.icore.IDecisionTable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.taHjaj.wo.jenoptron.model.core.binary.BinaryActionValue.DO;

public class DecisionTableTest extends AbstractTestCase {
	@Test
	public void testDefaultValue() {
		final IDecisionTable decisionTable = new DecisionTable(DO);

		final ICondition condition1 = new BinaryCondition("condition 1");
		final ICondition condition2 = new BinaryCondition("condition 2");

		final IAction action1 = new BinaryAction("action 1");
		final IAction action2 = new BinaryAction("action 2");
		final IDecisionTable decisionTable1 = decisionTable.add(condition1, condition2).add(action1, action2).split();

		{
			final String string = dump(decisionTable1);
			assertEquals("YYNN condition 1\n" + "YNYN condition 2\n" + "XXXX action 1\n" + "XXXX action 2\n", string);
		}

	}

	@Test
	public void testSetActionValues() {
		final ICondition condition1 = new BinaryCondition("condition 1");
		final ICondition condition2 = new BinaryCondition("condition 2");

		final IAction action1 = new BinaryAction("action 1");
		final IAction action2 = new BinaryAction("action 2");

		final IDecisionTable decisionTable = new DecisionTable(DO);

		{
			final String string = dump(decisionTable);
			assertEquals("", string);
		}

		final IDecisionTable decisionTable1a = decisionTable.add(condition1, condition2);

		{
			final String string = dump(decisionTable1a);
			assertEquals("YNYN condition 1\n" + "YYNN condition 2\n", string);
		}

		final IDecisionTable decisionTable1 = decisionTable1a.add(action1, action2).split();

		{
			final String string = dump(decisionTable1);
			assertEquals("YYNN condition 1\n" + "YNYN condition 2\n" + "XXXX action 1\n" + "XXXX action 2\n", string);
		}

		final IDecisionTable decisionTable2 = decisionTable1.setActionValues(BinaryActionValue.DONT);

		{
			final String string = dump(decisionTable2);
			assertEquals("YYNN condition 1\n" + "YNYN condition 2\n" + "     action 1\n" + "     action 2\n", string);
		}

		final IDecisionTable decisionTable2a = decisionTable2.setActionValues(BinaryActionValue.DO);

		{
			final String string = dump(decisionTable2a);
			assertEquals("YYNN condition 1\n" + "YNYN condition 2\n" + "XXXX action 1\n" + "XXXX action 2\n", string);
		}

		final IDecisionTable decisionTable3 = decisionTable2a.setActionValues(decisionTable2a.getRule(2),
				BinaryActionValue.DONT);

		{
			final String string = dump(decisionTable3);
			assertEquals("YYNN condition 1\n" + "YNYN condition 2\n" + "XX X action 1\n" + "XX X action 2\n", string);
		}

		final IDecisionTable decisionTable4 = decisionTable3.setActionValues(BinaryActionValue.DO,
				BinaryActionValue.DONT);

		{
			final String string = dump(decisionTable4);
			assertEquals("YYNN condition 1\n" + "YNYN condition 2\n" + "XXXX action 1\n" + "     action 2\n", string);
		}

		final IDecisionTable decisionTable5 = decisionTable4.setActionValues(BinaryActionValue.DO, BinaryActionValue.DO)
				.setActionValues(BinaryActionValue.DONT, action2);

		{
			final String string = dump(decisionTable5);
			assertEquals("YYNN condition 1\n" + "YNYN condition 2\n" + "XXXX action 1\n" + "     action 2\n", string);
		}

		final IDecisionTable decisionTable6 = decisionTable5.setActionValues(BinaryActionValue.DO, action2)
				.setActionValues(BinaryActionValue.DONT, BinaryActionValue.DO);

		{
			final String string = dump(decisionTable6);
			assertEquals("YYNN condition 1\n" + "YNYN condition 2\n" + "     action 1\n" + "XXXX action 2\n", string);
		}

	}
}
