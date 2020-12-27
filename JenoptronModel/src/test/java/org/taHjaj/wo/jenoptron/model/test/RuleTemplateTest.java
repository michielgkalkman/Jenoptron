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
import org.taHjaj.wo.jenoptron.model.icore.ICondition;
import org.taHjaj.wo.jenoptron.model.icore.IDecisionTable;
import org.taHjaj.wo.jenoptron.model.icore.IRule;

public class RuleTemplateTest extends AbstractTestCase {
	private static final Logger logger = Logger.getLogger(RuleTemplateTest.class);

	@Test
	public void testSimple2() {

		final IAction action = new BinaryAction();
		final IDecisionTable decisionTable = new DecisionTable().add(new BinaryCondition()).add(action);

		// decisionTable.split();

		final Iterable<IRule> rules = decisionTable.getRules();

		rules.iterator().next().setActionValue(action, BinaryActionValue.DO);

		logger.debug(dump(decisionTable));
	}

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
		{
			final IAction action = new BinaryAction();
			decisionTable.add(action);
		}

		logger.debug(dump(decisionTable));

		decisionTable.split();

		logger.debug(dump(decisionTable));

		decisionTable.split();

		logger.debug(dump(decisionTable));

		// Add condition
		{
			final ICondition condition = new BinaryCondition();
			decisionTable.add(condition);
		}

		logger.debug(dump(decisionTable));

		decisionTable.split();

		logger.debug(dump(decisionTable));

		// Add condition
		{
			final ICondition condition = new BinaryCondition();
			decisionTable.add(condition);
		}

		decisionTable.split();

		logger.debug(dump(decisionTable));

		logger.debug("Done.");
	}
}
