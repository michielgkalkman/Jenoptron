package jdt.core.test;


import jdt.core.DecisionTable;
import jdt.core.binary.BinaryAction;
import jdt.core.binary.BinaryActionValue;
import jdt.core.binary.BinaryCondition;
import jdt.core.junit.AbstractTestCase;
import jdt.icore.IAction;
import jdt.icore.ICondition;
import jdt.icore.IDecisionTable;
import jdt.icore.IRule;

import org.apache.log4j.Logger;

public class UpDownActionTest extends AbstractTestCase {
	private static final Logger logger = Logger.getLogger( UpDownActionTest.class);

	public void testSimple() {
		final IDecisionTable decisionTable = new DecisionTable();

		// Add condition
		{
			final ICondition condition = new BinaryCondition();
			decisionTable.add( condition);
		}

		// Add action
		{
			final IAction action = new BinaryAction();
			decisionTable.add( action);
		}

		// Add action
		IAction action;
		{
			action = new BinaryAction();
			decisionTable.add( action);
		}

		final IDecisionTable decisionTable2 = decisionTable.deepcopy();

		decisionTable.up( action);
		decisionTable.down( action);

		assertEquals( decisionTable, decisionTable2);

		logger.debug( dump( decisionTable));
		logger.debug( dump( decisionTable2));
	}

	public void testSimple2() {
		final IDecisionTable decisionTable = new DecisionTable();

		// Add condition
		{
			final ICondition condition = new BinaryCondition();
			decisionTable.add( condition);
		}

		// Add condition
		ICondition condition;
		{
			condition = new BinaryCondition();
			decisionTable.add( condition);
		}

		// Add action
		{
			final IAction action = new BinaryAction();
			decisionTable.add( action);
		}

		final IDecisionTable decisionTable2 = decisionTable.deepcopy();

		decisionTable.up( condition);
		decisionTable.down( condition);

		decisionTable.reduce();
		decisionTable2.reduce();


		logger.debug( dump( decisionTable));
		logger.debug( dump( decisionTable2));

		assertEquals( decisionTable, decisionTable2);
	}
	public void testSimple3() {
		logger.debug( "testSimple3()");

		final IDecisionTable decisionTable = new DecisionTable();

		// Add condition
		{
			final ICondition condition = new BinaryCondition( "c1");
			decisionTable.add( condition);
		}

		// Add condition
		final ICondition condition2 = new BinaryCondition( "c2");
		decisionTable.add( condition2);

		// Add action
		final IAction action;
		{
			action = new BinaryAction();
			decisionTable.add( action);
		}

		decisionTable.split();

		{
			final IRule rule = decisionTable.getRule(0);
			rule.setActionValue( action, BinaryActionValue.DO);
		}
		{
			final IRule rule = decisionTable.getRule(1);
			rule.setActionValue( action, BinaryActionValue.DO);
		}
		{
			final IRule rule = decisionTable.getRule(2);
			rule.setActionValue( action, BinaryActionValue.DONT);
		}
		{
			final IRule rule = decisionTable.getRule(3);
			rule.setActionValue( action, BinaryActionValue.DO);
		}
		{
			final String string = dump( decisionTable);
			logger.debug( string);
			assertEquals( "YYNN c1\n" +
					"YNYN c2\n" +
					"XX X BinaryAction\n", string);
		}

		decisionTable.up(condition2);

		{
			final String string = dump( decisionTable);
			logger.debug( string);
			assertEquals( "YYNN c2\n" +
					"YNYN c1\n" +
					"X XX BinaryAction\n", string);
		}

		decisionTable.down(condition2);
		{
			final String string = dump( decisionTable);
			logger.debug( string);
			assertEquals( "YYNN c1\n" +
					"YNYN c2\n" +
					"XX X BinaryAction\n", string);
		}

		decisionTable.reduce();
		{
			final String string = dump( decisionTable);
			logger.debug( string);
			assertEquals( "YNN c1\n" +
					"-YN c2\n" +
					"X X BinaryAction\n", string);
		}

		decisionTable.up(condition2);

		{
			final String string = dump( decisionTable);
			logger.debug( string);
			assertEquals( "YYNN c2\n" +
					"YNYN c1\n" +
					"X XX BinaryAction\n", string);
		}
	}

	public void testSimpleWithList() {
		final IDecisionTable decisionTable = new DecisionTable();

		// Add condition
		{
			decisionTable.add( getConditionList( "a", "b", "c"));

			final BinaryAction binaryAction = new BinaryAction("1");
			decisionTable.add( binaryAction);

			decisionTable.split();

			decisionTable.setActionValues( BinaryActionValue.DO);
			decisionTable.split();
			decisionTable.getRule( 0).setActionValue( binaryAction, BinaryActionValue.DONT);

			{
				final String string = dump( decisionTable);
				logger.debug( string);
				assertEquals( "YYYYNNNN a\n" +
						"YYNNYYNN b\n" +
						"YNYNYNYN c\n" +
						" XXXXXXX 1\n", string);
			}

			decisionTable.reduce();

			{
				final String string = dump( decisionTable);
				logger.debug( string);
				assertEquals( "YN-- a\n" +
						"Y-N- b\n" +
						"Y--N c\n" +
						" XXX 1\n", string);
			}
		}
	}

	public void testSimpleWithList2() {
		final IDecisionTable decisionTable = new DecisionTable();

		// Add condition
		{
			decisionTable.add( getConditionList( "a", "b"));

			final BinaryAction binaryAction = new BinaryAction("1");
			decisionTable.add( binaryAction);

			decisionTable.split();

			decisionTable.setActionValues( BinaryActionValue.DO);
			decisionTable.split();
			decisionTable.getRule( 1).setActionValue( binaryAction, BinaryActionValue.DONT);

			{
				final String string = dump( decisionTable);
				logger.debug( string);
				assertEquals( "YYNN a\n" +
						"YNYN b\n" +
						"X XX 1\n", string);
			}

			decisionTable.reduce();
			decisionTable.split();

			{
				final String string = dump( decisionTable);
				logger.debug( string);
				assertEquals( "YYNN a\n" +
						"YNYN b\n" +
						"X XX 1\n", string);
			}
		}
	}
}
