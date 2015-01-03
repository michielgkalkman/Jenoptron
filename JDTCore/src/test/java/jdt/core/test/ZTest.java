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
