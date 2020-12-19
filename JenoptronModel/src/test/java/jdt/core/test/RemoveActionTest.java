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

import org.taHjaj.wo.jenoptron.model.core.DecisionTable;
import org.taHjaj.wo.jenoptron.model.core.binary.BinaryAction;
import org.taHjaj.wo.jenoptron.model.core.binary.BinaryCondition;
import jdt.core.junit.AbstractTestCase;
import org.taHjaj.wo.jenoptron.model.icore.IAction;
import org.taHjaj.wo.jenoptron.model.icore.ICondition;
import org.taHjaj.wo.jenoptron.model.icore.IDecisionTable;

public class RemoveActionTest extends AbstractTestCase {
	private static final Logger logger = Logger.getLogger(RemoveActionTest.class);

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

		decisionTable.remove(action);

		logger.debug(dump(decisionTable));
	}

	@Test
	public void testSimple2() {
		final IAction action = new BinaryAction();

		final DecisionTable emptyDecisionTable = new DecisionTable();
		assertEquals("", dump(emptyDecisionTable));

		final IDecisionTable decisionTableWithCondition = emptyDecisionTable.add(new BinaryCondition());
		assertEquals("YN BinaryCondition\n", dump(decisionTableWithCondition));

		final IDecisionTable add = decisionTableWithCondition.add(new BinaryAction());
		final IDecisionTable decisionTable = add.add(action);

		assertEquals("YN BinaryCondition\n?? BinaryAction\n?? BinaryAction\n", dump(decisionTable));

		final IDecisionTable decisionTable1 = decisionTable.reduce();
		final String before = dump(decisionTable1);

		assertEquals("- BinaryCondition\n? BinaryAction\n? BinaryAction\n", before);

		final IDecisionTable decisionTable1a = decisionTable1.remove(action);

		{
			final String string = dump(decisionTable1a);
			assertEquals("- BinaryCondition\n? BinaryAction\n", string);
		}

		final IDecisionTable decisionTable2 = decisionTable1a.add(action);

		{
			final String string = dump(decisionTable2);
			assertEquals("- BinaryCondition\n? BinaryAction\n? BinaryAction\n", string);
		}

		final String after = dump(decisionTable2);

		assertEquals(before, after);
	}
}
