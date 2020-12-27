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
import org.taHjaj.wo.jenoptron.model.icore.IAction;
import org.taHjaj.wo.jenoptron.model.icore.IDecisionTable;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ActionTest extends AbstractTestCase {
	
	@Test
	public void testParseCondition() {
		final IDecisionTable decisionTable = new DecisionTable();
		
		// Add action
		final IAction action;
		{
			action = new BinaryAction();
			decisionTable.add( action);
		}

		assertEquals( BinaryActionValue.UNKNOWN, action.parse( "?"));
		assertEquals( BinaryActionValue.UNKNOWN, action.parse( "WW"));
	}
}
