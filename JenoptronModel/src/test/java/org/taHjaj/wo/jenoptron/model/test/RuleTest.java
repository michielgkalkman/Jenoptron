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
import org.taHjaj.wo.jenoptron.model.core.Rule;
import org.taHjaj.wo.jenoptron.model.core.binary.BinaryAction;
import org.taHjaj.wo.jenoptron.model.core.binary.BinaryCondition;
import org.taHjaj.wo.jenoptron.model.core.binary.BinaryConditionValue;
import org.taHjaj.wo.jenoptron.model.icore.ICondition;
import org.taHjaj.wo.jenoptron.model.icore.IRule;

import static org.junit.jupiter.api.Assertions.*;

public class RuleTest extends AbstractTestCase {
	private static final Logger logger = Logger.getLogger(RuleTest.class);

	@Test
	public void testCompare() {
		final IRule rule;
		final IRule rule2;

		{
			rule = new Rule();
		}

		{
			rule2 = new Rule();
		}

		assertEquals(rule2, rule);

		logger.debug(rule);
	}

	@Test
	public void testCompare2() {
		final IRule rule;
		final IRule rule2;

		{
			rule = new Rule().addCondition(new BinaryCondition());
		}

		{
			rule2 = new Rule();
		}

		assertNotEquals(rule2, rule);

		logger.debug(rule);
	}

	@Test
	public void testCompare3() {
		final IRule rule;
		final IRule rule2;

		{
			rule = new Rule().addAction(new BinaryAction());
		}

		{
			rule2 = new Rule();
		}

		assertNotEquals(rule2, rule);

		logger.debug(rule);
	}

	@Test
	public void testCompare4() {
		final IRule rule;
		final IRule rule2;

		{
			rule = new Rule().addAction(new BinaryAction());
		}

		{
			rule2 = new Rule().addCondition(new BinaryCondition());
		}

		assertNotEquals(rule2, rule);

		logger.debug(rule);
	}

	@Test
	public void testCompare5() {
		final IRule rule;
		final IRule rule2;

		final ICondition condition = new BinaryCondition();

		{
			rule = new Rule();
			rule.addCondition(condition);
		}

		{
			rule2 = new Rule();
			rule2.addCondition(condition);
		}

		assertEquals(rule2, rule);

		logger.debug(rule);
	}

	@Test
	public void testCompare6() {
		final IRule rule;
		final IRule rule2;

		final ICondition condition = new BinaryCondition();

		{
			rule = new Rule().addCondition(condition).setConditionValue(condition, BinaryConditionValue.NO);
		}

		{
			rule2 = new Rule().addCondition(condition).setConditionValue(condition, BinaryConditionValue.NO);
		}

		assertEquals(rule2, rule);

		logger.debug(rule);
	}

	@Test
	public void testCompare7() {
		final IRule rule;
		final IRule rule2;

		final ICondition condition = new BinaryCondition();
		final ICondition condition2 = new BinaryCondition();

		{
			rule = new Rule().addCondition(condition).addCondition(condition2)
					.setConditionValue(condition, BinaryConditionValue.NO)
					.setConditionValue(condition2, BinaryConditionValue.YES);
		}

		{
			rule2 = new Rule().addCondition(condition).addCondition(condition2)
					.setConditionValue(condition, BinaryConditionValue.NO)
					.setConditionValue(condition2, BinaryConditionValue.YES);
		}

		assertEquals(rule2, rule);

		logger.debug(rule);
	}
}
