package jdt.core.test;

import static jdt.core.binary.BinaryActionValue.DO;
import jdt.core.DecisionTable;
import jdt.core.binary.BinaryAction;
import jdt.core.binary.BinaryActionValue;
import jdt.core.binary.BinaryCondition;
import jdt.core.junit.AbstractTestCase;
import jdt.icore.IAction;
import jdt.icore.ICondition;
import jdt.icore.IDecisionTable;

import org.junit.Test;

public class DecisionTableTest extends AbstractTestCase {
	@Test
	public void testDefaultValue() {
		final IDecisionTable decisionTable = new DecisionTable( DO);
		
		final ICondition condition1 = new BinaryCondition("condition 1");
		final ICondition condition2 = new BinaryCondition("condition 2");

		final IAction action1 = new BinaryAction("action 1");
		final IAction action2 = new BinaryAction("action 2");
		decisionTable
			.add(condition1, condition2)
			.add(action1, action2)
			.split();
		

		{
			final String string = dump( decisionTable);
			assertEquals( "YYNN condition 1\n" +
					"YNYN condition 2\n" +
					"XXXX action 1\n" +
					"XXXX action 2\n", string);
		}

	}
	
	@Test
	public void testSetActionValues() {
		final IDecisionTable decisionTable = new DecisionTable( DO);
		
		final ICondition condition1 = new BinaryCondition("condition 1");
		final ICondition condition2 = new BinaryCondition("condition 2");

		final IAction action1 = new BinaryAction("action 1");
		final IAction action2 = new BinaryAction("action 2");
		decisionTable
			.add(condition1, condition2)
			.add(action1, action2)
			.split();
		

		{
			final String string = dump( decisionTable);
			assertEquals( "YYNN condition 1\n" +
					"YNYN condition 2\n" +
					"XXXX action 1\n" +
					"XXXX action 2\n", string);
		}
		
		decisionTable.setActionValues( BinaryActionValue.DONT);
		
		{
			final String string = dump( decisionTable);
			assertEquals( "YYNN condition 1\n" +
					"YNYN condition 2\n" +
					"     action 1\n" +
					"     action 2\n", string);
		}
		
		decisionTable.setActionValues( BinaryActionValue.DO).setActionValues( decisionTable.getRule(2), BinaryActionValue.DONT);
		
		{
			final String string = dump( decisionTable);
			assertEquals( "YYNN condition 1\n" +
					"YNYN condition 2\n" +
					"XX X action 1\n" +
					"XX X action 2\n", string);
		}
		
		decisionTable.setActionValues( BinaryActionValue.DO, BinaryActionValue.DONT);
		
		{
			final String string = dump( decisionTable);
			assertEquals( "YYNN condition 1\n" +
					"YNYN condition 2\n" +
					"XXXX action 1\n" +
					"     action 2\n", string);
		}
		
		decisionTable.setActionValues( BinaryActionValue.DO, BinaryActionValue.DO)
		.setActionValues( BinaryActionValue.DONT, action2);
	
		{
			final String string = dump( decisionTable);
			assertEquals( "YYNN condition 1\n" +
					"YNYN condition 2\n" +
					"XXXX action 1\n" +
					"     action 2\n", string);
		}
		
		decisionTable.setActionValues( BinaryActionValue.DO, action2)
			.setActionValues( BinaryActionValue.DONT, BinaryActionValue.DO);
	
		{
			final String string = dump( decisionTable);
			assertEquals( "YYNN condition 1\n" +
					"YNYN condition 2\n" +
					"     action 1\n" +
					"XXXX action 2\n", string);
		}

	}
}
