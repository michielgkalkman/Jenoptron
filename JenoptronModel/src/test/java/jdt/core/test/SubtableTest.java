package jdt.core.test;

import static jdt.core.binary.BinaryActionValue.DO;
import static jdt.core.binary.BinaryActionValue.DONT;
import static jdt.core.binary.BinaryConditionValue.NO;
import static jdt.core.binary.BinaryConditionValue.YES;

import java.util.List;
import java.util.Map;

import jdt.core.DecisionTable;
import jdt.core.binary.BinaryAction;
import jdt.core.binary.BinaryActionValue;
import jdt.core.binary.BinaryCondition;
import jdt.core.junit.AbstractTestCase;
import jdt.icore.IAction;
import jdt.icore.ICondition;
import jdt.icore.IConditionValue;
import jdt.icore.IDecisionTable;
import jdt.icore.IRule;

import org.apache.log4j.Logger;
import org.junit.Test;

public class SubtableTest extends AbstractTestCase {
	private static final Logger logger = Logger.getLogger( PropertyChangeTest.class);

	@Test
	public void testGetConditionalRules() {
		final ICondition condition1 = new BinaryCondition( "bin1");
		final ICondition condition2 = new BinaryCondition( "bin2");
		final ICondition condition3 = new BinaryCondition( "bin3");

		final IDecisionTable decisionTable = new DecisionTable()
			.add(condition1)
			.add(condition2)
			.add(condition3);

		decisionTable.split();
		
		final Map<ICondition,IConditionValue> conditions = decisionTable.getRule(1).getConditions();

		conditions.remove( condition1);
		
		final List<IRule> interestingRules = decisionTable.getRules( conditions);
		
		logger.debug(interestingRules);
	}
	
	@Test
	public void testSimple() {
		final IDecisionTable decisionTable = new DecisionTable();

		// Add condition
		{
			final ICondition condition = new BinaryCondition( "bin1");
			decisionTable.add( condition);
		}

		// Add action
		{
			final IAction action = new BinaryAction( "ac1");
			decisionTable.add( action);
		}

		// Add action
		IAction action;
		{
			action = new BinaryAction( "ac2");
			decisionTable.add( action);
		}

		decisionTable.split();
		decisionTable.getRule(1).setActionValue( action, BinaryActionValue.DO);

		final int[] conditionRows = {0};
		final int[] actionRows = {1};

		final IDecisionTable decisionTable2 = decisionTable.getSubtable( conditionRows, actionRows);

		decisionTable2.split();

		logger.debug( dump( decisionTable));
		logger.debug( dump( decisionTable2));
		
		{
			final String string = dump( decisionTable);
			logger.debug( '\n' + string);
			assertEquals( "YN bin1\n" +
					"?? ac1\n" +
					"?X ac2\n", string);
		}
		
		{
			final String string = dump( decisionTable2);
			logger.debug( '\n' + string);
			assertEquals( "YN bin1\n" +
					"?X ac2\n", string);
		}
	}
	
	@Test
	public void testSimple2() {
		final IDecisionTable decisionTable = new DecisionTable();
		
		decisionTable
			.setDefaultActionValue( DONT)
			.addBinaryActions( "action1", "action2")
			.addBinaryConditions( "condition1", "condition2", "condition3")
			.split()
			.setActionValues( decisionTable.getRule( YES, NO, NO), DO, DONT)
			.setActionValues( decisionTable.getRule( NO, NO, NO), DONT, DONT);

		final IDecisionTable decisionTable2 = decisionTable.getSubtable( "condition2", "condition3");
		
		{
			final String string = dump( decisionTable);
			logger.debug( '\n' + string);
			assertEquals( 
					"YYYYNNNN condition1\n" +
					"YYNNYYNN condition2\n" +
					"YNYNYNYN condition3\n" +
					"???X???  action1\n" +
					"??? ???  action2\n", string);
		}
		
		{
			final String string = dump( decisionTable2);
			logger.debug( '\n' + string);
			assertEquals( "YYNN condition2\n" +
					"YNYN condition3\n" +
					"???? action1\n" +
					"???  action2\n", string);
		}
	}
}
