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
import org.taHjaj.wo.jenoptron.model.core.category.ExclusiveConditionsGroup;
import org.taHjaj.wo.jenoptron.model.core.category.IExclusiveConditionGroup;
import org.taHjaj.wo.jenoptron.model.icore.IDecisionTable;
import org.taHjaj.wo.jenoptron.model.icore.IRule;

import static org.junit.jupiter.api.Assertions.*;

public class CategoryTest extends AbstractTestCase {
	private static final Logger logger = Logger.getLogger(CategoryTest.class);

	@Test
	public void testSimple() {
		final IExclusiveConditionGroup categoryGroup = new ExclusiveConditionsGroup("Test")
				.add(new BinaryCondition("A")).add(new BinaryCondition("B"));

		final IDecisionTable decisionTable = new DecisionTable().add(categoryGroup);

		{
			final String string = dump(decisionTable);
			assertEquals("YN A\n" + "NY B\n", string);
			logger.debug("\n" + string);
		}

		final IDecisionTable decisionTable2 = decisionTable.reduce();

		{
			final String string = dump(decisionTable2);
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
			assertEquals("YNN A\n" + "NYN B\n" + "NNY C\n" + "XXX abc\n", string);
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

		final IDecisionTable decisionTable1a = decisionTable.add(categoryGroup);

		{
			final String string = dump(decisionTable1a);
			assertEquals("YNN A\n" + "NYN B\n" + "NNY C\n", string);
			logger.debug(string);
		}

		final IDecisionTable decisionTable2 = decisionTable1a.split();

		{
			final String string = dump(decisionTable2);
			assertEquals("YNN A\n" + "NYN B\n" + "NNY C\n", string);
			logger.debug(string);
		}

		final IDecisionTable decisionTable3 = decisionTable2.reduce().split();

		{
			final String string = dump(decisionTable3);
			assertEquals("YNN A\n" + "NYN B\n" + "NNY C\n", string);
			logger.debug(string);
		}
	}

	@Test
	public void testSimple4() {
		final IExclusiveConditionGroup categoryGroup = new ExclusiveConditionsGroup("Test")
				.add(new BinaryCondition("A")).add(new BinaryCondition("B"));

		final IDecisionTable decisionTable = new DecisionTable().add(categoryGroup).add(new BinaryCondition("C"))
				.split();

		{
			final String string = dump(decisionTable);
			assertEquals("YYNN A\n" + "NNYY B\n" + "YNYN C\n", string);
			logger.debug("\n" + string);
		}

		final IDecisionTable reduce = decisionTable.reduce();
		final IDecisionTable decisionTable2 = reduce.split().reduce();

		{
			final String string = dump(decisionTable2);
			assertEquals("- A\n" + "- B\n" + "- C\n", string);
			logger.debug("\n" + string);
		}

		final IDecisionTable decisionTable3 = decisionTable2.split();

		{
			final String string = dump(decisionTable3);
			assertEquals("YYNN A\n" + "NNYY B\n" + "YNYN C\n", string);
			logger.debug("\n" + string);
		}

		final BinaryAction abc = new BinaryAction("abc");
		final IDecisionTable decisionTable4 = decisionTable.add(abc);
		{
			final IRule rule = decisionTable4.getRule(BinaryConditionValue.YES, BinaryConditionValue.YES,
					BinaryConditionValue.YES);
			assertNull(rule);
		}

		final IDecisionTable decisionTable5 = decisionTable.setActionValues(BinaryActionValue.DO);

		final IRule rule = decisionTable5.getRule(BinaryConditionValue.YES, BinaryConditionValue.NO,
				BinaryConditionValue.YES);
		assertNotNull(rule);
		rule.setActionValue(abc, BinaryActionValue.DONT);

		{
			final String string = dump(decisionTable5);

			logger.debug("\n" + string);
		}

		final IDecisionTable decisionTable6 = decisionTable5.reduce();

		{
			final String string = dump(decisionTable6);

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
