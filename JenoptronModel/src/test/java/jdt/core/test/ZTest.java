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

import jdt.core.DecisionTable;
import jdt.core.binary.BinaryAction;
import jdt.core.binary.BinaryCondition;
import jdt.core.junit.AbstractTestCase;
import jdt.icore.IAction;
import jdt.icore.ICondition;
import jdt.icore.IDecisionTable;

import org.apache.log4j.Logger;
import org.junit.Test;

public class ZTest extends AbstractTestCase {
	private static final Logger logger = Logger.getLogger( ZTest.class);

	@Test
	public void testParseCondition() {
		final IDecisionTable decisionTable = new DecisionTable();

		final ICondition A = new BinaryCondition( "A");
		final ICondition B = new BinaryCondition( "B");
		final IAction action = new BinaryAction( "X");
		decisionTable.add( action);
		decisionTable.add( A);
		decisionTable.add( B);
		
		decisionTable.split();
		
		logger.debug( dump(decisionTable));
	}
}
