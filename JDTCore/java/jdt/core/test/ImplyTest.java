package jdt.core.test;

import jdt.core.DecisionTable;
import jdt.core.binary.BinaryAction;
import jdt.core.binary.BinaryCondition;
import jdt.core.category.IImplyGroup;
import jdt.core.category.ImplyGroup;
import jdt.core.junit.AbstractTestCase;
import jdt.icore.IAction;
import jdt.icore.ICondition;
import jdt.icore.IDecisionTable;

import org.apache.log4j.Logger;

public class ImplyTest extends AbstractTestCase {
	public static final Logger logger = Logger.getLogger( ImplyTest.class);

	public void testSimple() {
		logger.debug( "TestSimple");

		final IDecisionTable decisionTable  = new DecisionTable();

		final IImplyGroup implyGroup = new ImplyGroup();

		final ICondition A = new BinaryCondition( "A");
		final ICondition B = new BinaryCondition( "B");
		final IAction action = new BinaryAction( "X");
		decisionTable.add( action);

		decisionTable.reduce();
//		logger.debug( dump( decisionTable));

		implyGroup.add( A);
		implyGroup.implies( B);

		decisionTable.add( implyGroup);

		logger.debug( dump( decisionTable));

		decisionTable.split();

		final String tableTekst = dump( decisionTable);

		assertEquals(
				"YNN A\n" +
				"YYN B\n" +
				"??? X\n",
			 tableTekst);

		logger.debug( dump( decisionTable));
		logger.debug( "TestSimple");
	}

	public void testSimple2() {
		logger.debug( "TestSimple2");
		final IDecisionTable decisionTable  = new DecisionTable();

		final IImplyGroup implyGroup = new ImplyGroup();

		final ICondition A = new BinaryCondition( "A");
		new BinaryCondition( "B");
		final IAction F = new BinaryAction( "F");

		implyGroup.add( A);
		implyGroup.implies( F);

		decisionTable.add( implyGroup);

		decisionTable.reduce();

		final String tableTekst = dump( decisionTable);

		assertEquals( tableTekst,
				"YN A\n" +
				"X? F\n");

		decisionTable.split();

		logger.debug( dump( decisionTable));
		logger.debug( "TestSimple2");
	}

	public void testSimple2a() {
		logger.debug( "TestSimple2a");
		final IDecisionTable decisionTable  = new DecisionTable();

		final IImplyGroup implyGroup = new ImplyGroup();

		final ICondition A = new BinaryCondition( "A");
		final ICondition B1 = new BinaryCondition( "B1");
		final ICondition B2 = new BinaryCondition( "B2");
		final IAction F = new BinaryAction( "F");

		implyGroup.add( A);
		implyGroup.implies( F);

		decisionTable.add( B1);
		decisionTable.add( B2);

		{
			final String tableTekst = dump( decisionTable);

			logger.debug( tableTekst);

			assertEquals(
					"YN B1\n" +
					"-- B2\n", tableTekst);
		}

		decisionTable.add( implyGroup);

		{
			final String tableTekst = dump( decisionTable);
			logger.debug( tableTekst);

			assertEquals(
					"YYYYNNNN B1\n" +
					"YYNNYYNN B2\n" +
					"YNYNYNYN A\n" +
					"X?X?X?X? F\n", tableTekst);
		}
	}

	public void testSimple3() {
		logger.debug( "TestSimple3");
		final IDecisionTable decisionTable  = new DecisionTable();

		final IImplyGroup implyGroup = new ImplyGroup();

		final ICondition A = new BinaryCondition( "A");
		final ICondition B = new BinaryCondition( "B");
		final IAction F = new BinaryAction( "F");
		final IAction G = new BinaryAction( "G");

		implyGroup.add( F);
		implyGroup.implies( G);

		decisionTable.add( implyGroup);
		decisionTable.add( A);
		decisionTable.add( B);

		decisionTable.reduce();

		logger.debug( dump( decisionTable));
		logger.debug( "TestSimple3");
	}
}
