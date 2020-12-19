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

import org.junit.Test;

import org.taHjaj.wo.jenoptron.model.core.DecisionTable;
import org.taHjaj.wo.jenoptron.model.core.binary.BinaryCondition;
import jdt.core.junit.AbstractTestCase;
import org.taHjaj.wo.jenoptron.model.icore.ICondition;

public class DoubleConditionTest extends AbstractTestCase {

	@Test
	public void testSimple() {
		final ICondition condition = new BinaryCondition();

		try {
			new DecisionTable().add(condition).add(condition);
			fail();
		} catch (final RuntimeException e) {
		}
	}
}
