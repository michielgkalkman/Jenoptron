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

import static org.taHjaj.wo.jenoptron.model.core.binary.BinaryActionValue.DO;
import static org.taHjaj.wo.jenoptron.model.core.binary.BinaryActionValue.DONT;
import static org.taHjaj.wo.jenoptron.model.core.binary.BinaryConditionValue.NO;
import static org.taHjaj.wo.jenoptron.model.core.binary.BinaryConditionValue.YES;

import org.apache.log4j.Logger;
import org.junit.Test;

import org.taHjaj.wo.jenoptron.model.core.DecisionTable;
import jdt.core.junit.AbstractTestCase;
import org.taHjaj.wo.jenoptron.model.icore.IDecisionTable;

public class OtherwiseTest extends AbstractTestCase {
	private static final Logger logger = Logger.getLogger(OtherwiseTest.class);

	@Test
	public void testFluentInterface() {
		final IDecisionTable decisionTable = new DecisionTable().setDefaultActionValue(DONT)
				.addBinaryActions("action1", "action2").addBinaryConditions("condition1", "condition2").split();

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
		final IDecisionTable decisionTable = new DecisionTable(DO).addBinaryConditions("condition1", "condition2")
				.addBinaryActions("action1", "action2").split();

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
