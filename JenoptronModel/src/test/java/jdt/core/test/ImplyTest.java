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
import org.junit.Ignore;
import org.junit.Test;

import jdt.core.DecisionTable;
import jdt.core.binary.BinaryAction;
import jdt.core.binary.BinaryActionValue;
import jdt.core.binary.BinaryCondition;
import jdt.core.category.IImplyGroup;
import jdt.core.category.ImplyGroup;
import jdt.core.junit.AbstractTestCase;
import jdt.icore.IAction;
import jdt.icore.ICondition;
import jdt.icore.IDecisionTable;

@Ignore
public class ImplyTest extends AbstractTestCase {
	public static final Logger logger = Logger.getLogger(ImplyTest.class);

	@Test
	public void testSimple() {
		logger.debug("TestSimple");

		final IDecisionTable decisionTable = new DecisionTable();

		final IImplyGroup implyGroup = new ImplyGroup();

		final ICondition A = new BinaryCondition("A");
		final ICondition B = new BinaryCondition("B");
		final IAction action = new BinaryAction("X");
		decisionTable.add(action);

		decisionTable.reduce();
		// logger.debug( dump( decisionTable));

		implyGroup.add(A);
		implyGroup.implies(B);

		decisionTable.add(implyGroup);

		logger.debug(dump(decisionTable));

		decisionTable.split();

		final String tableTekst = dump(decisionTable);

		assertEquals("YNN A\n" + "YYN B\n" + "??? X\n", tableTekst);

		logger.debug(dump(decisionTable));
		logger.debug("TestSimple");
	}

	@Ignore // Groups don't work yet.
	@Test
	public void testSimple2() {
		logger.debug("TestSimple2");
		final IDecisionTable decisionTable = new DecisionTable();

		final IImplyGroup implyGroup = new ImplyGroup();

		final ICondition A = new BinaryCondition("A");
		new BinaryCondition("B");
		final IAction F = new BinaryAction("F");

		implyGroup.add(A);
		implyGroup.implies(F);

		decisionTable.add(implyGroup);

		decisionTable.reduce();

		final String tableTekst = dump(decisionTable);

		assertEquals(tableTekst, "YN A\n" + "X? F\n");

		decisionTable.split();

		logger.debug(dump(decisionTable));
		logger.debug("TestSimple2");
	}

	@Ignore // Groups don't work yet.
	@Test
	public void testSimple2a() {
		logger.debug("TestSimple2a");
		final IDecisionTable decisionTable = new DecisionTable();

		assertFalse(decisionTable.hasGroups());

		final IImplyGroup implyGroup = new ImplyGroup();

		final ICondition A = new BinaryCondition("A");
		final ICondition B1 = new BinaryCondition("B1");
		final ICondition B2 = new BinaryCondition("B2");
		final IAction F = new BinaryAction("F");

		implyGroup.add(A);
		implyGroup.implies(F);

		decisionTable.add(B1);
		decisionTable.add(B2);

		{
			final String tableTekst = dump(decisionTable);

			logger.debug(tableTekst);

			assertEquals("YN B1\n" + "-- B2\n", tableTekst);
		}

		decisionTable.add(implyGroup);

		assertTrue(decisionTable.hasGroups());

		{
			final String tableTekst = dump(decisionTable);
			logger.debug(tableTekst);

			assertEquals("YYYYNNNN B1\n" + "YYNNYYNN B2\n" + "YNYNYNYN A\n" + "X?X?X?X? F\n", tableTekst);
		}
	}

	@Ignore // Groups don't work yet.
	@Test
	public void testSimple3() {
		logger.debug("TestSimple3");
		final IDecisionTable decisionTable = new DecisionTable();

		final IImplyGroup implyGroup = new ImplyGroup();

		final ICondition A = new BinaryCondition("A");
		final ICondition B = new BinaryCondition("B");
		final IAction F = new BinaryAction("F");
		final IAction G = new BinaryAction("G");

		implyGroup.add(F);
		implyGroup.implies(G);

		decisionTable.add(implyGroup);
		decisionTable.add(A);
		decisionTable.add(B);

		// decisionTable.reduce();
		//
		// {
		// final String tableTekst = dump( decisionTable);
		// logger.debug( tableTekst);
		//
		// assertEquals(
		// "- A\n" +
		// "- B\n" +
		// "? F\n" +
		// "? G\n", tableTekst);
		// }
		//
		// decisionTable.split();
		//
		// {
		// final String tableTekst = dump( decisionTable);
		// logger.debug( tableTekst);
		//
		// assertEquals(
		// "YYNN A\n" +
		// "YNYN B\n" +
		// "???? F\n" +
		// "???? G\n", tableTekst);
		// }

		decisionTable.getRule(0).setActionValue(F, BinaryActionValue.DO);

		{
			final String tableTekst = dump(decisionTable);
			logger.debug(tableTekst);

			assertEquals("YYNN A\n" + "YNYN B\n" + "X??? F\n" + "X??? G\n", tableTekst);
		}

	}
}
