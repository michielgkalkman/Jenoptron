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
import org.taHjaj.wo.jenoptron.model.icore.IAction;
import org.taHjaj.wo.jenoptron.model.icore.IDecisionTable;
import org.taHjaj.wo.jenoptron.model.icore.IRule;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResetActionTest extends AbstractTestCase {
	private static Logger logger = Logger.getLogger(ResetActionTest.class);

	@Test
	public void testResetAction() {

		// Add action
		final IAction action = new BinaryAction();

		final IDecisionTable decisionTable = new DecisionTable().add(new BinaryCondition()).add(action);

		{
			final String string = dump(decisionTable);
			logger.debug(string);
			assertEquals("YN BinaryCondition\n" + "?? BinaryAction\n", string);
		}

		{
			final IRule rule = decisionTable.getRule(0);
			rule.setActionValue(action, BinaryActionValue.DO);

			final String string = dump(decisionTable);
			logger.debug(string);
			assertEquals("YN BinaryCondition\n" + "X? BinaryAction\n", string);
		}

		{
			final IRule rule = decisionTable.getRule(0);
			rule.setActionValue(action, BinaryActionValue.UNKNOWN);

			final String string = dump(decisionTable);
			logger.debug(string);
			assertEquals("YN BinaryCondition\n" + "?? BinaryAction\n", string);
		}
	}
}
