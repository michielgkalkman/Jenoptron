package jdt.core.test;

import jdt.core.DecisionTable;
import jdt.core.binary.BinaryAction;
import jdt.core.binary.BinaryActionValue;
import jdt.core.binary.BinaryCondition;
import jdt.core.category.ExclusiveConditionsGroup;
import jdt.core.category.IExclusiveConditionGroup;
import jdt.core.junit.AbstractTestCase;
import jdt.icore.IDecisionTable;

import org.apache.log4j.Logger;

public class CategoryTest extends AbstractTestCase {
	private static final Logger logger = Logger.getLogger( CategoryTest.class);

	public void testSimple() {
		final IDecisionTable decisionTable  = new DecisionTable();

		final IExclusiveConditionGroup categoryGroup = new ExclusiveConditionsGroup( "Test");

		categoryGroup.add( new BinaryCondition( "A"));
		categoryGroup.add( new BinaryCondition( "B"));

		decisionTable.add( categoryGroup);

		{
			final String string = dump( decisionTable);
			assertEquals(
					"YN A\n" +
					"NY B\n", string);
			logger.debug( string);
		}

		decisionTable.reduce();

		{
			final String string = dump( decisionTable);
			assertEquals(
					"- A\n" +
					"- B\n", string);
			logger.debug( string);
		}
	}

	public void testSimple2() {
		final IDecisionTable decisionTable  = new DecisionTable();

		final IExclusiveConditionGroup categoryGroup = new ExclusiveConditionsGroup( "Test");

		categoryGroup.add( new BinaryCondition( "A"));
		categoryGroup.add( new BinaryCondition( "B"));
		categoryGroup.add( new BinaryCondition( "C"));

		decisionTable.add( categoryGroup);
		
		decisionTable.add( new BinaryAction( "abc"));
		decisionTable.setActionValues( BinaryActionValue.DO);
		
		decisionTable.split();

		{
			final String string = dump( decisionTable);
			assertEquals(
					"YNN A\n" +
					"NYN B\n" +
					"NNY C\n" +
					"XXX abc\n", string);
			logger.debug( string);
		}

		decisionTable.reduce();

		{
			final String string = dump( decisionTable);
			assertEquals(
					"- A\n" +
					"- B\n" +
					"- C\n" +
					"X abc\n", string);
			logger.debug( string);
		}
	}

	public void testSimple3() {
		final IDecisionTable decisionTable  = new DecisionTable();

		final IExclusiveConditionGroup categoryGroup = new ExclusiveConditionsGroup( "Test");

		categoryGroup.add( new BinaryCondition( "A"));
		categoryGroup.add( new BinaryCondition( "B"));
		categoryGroup.add( new BinaryCondition( "C"));

		decisionTable.add( categoryGroup);

		decisionTable.split();

		{
			final String string = dump( decisionTable);
			assertEquals(
					"YNN A\n" +
					"NYN B\n" +
					"NNY C\n", string);
			logger.debug( string);
		}

		decisionTable.reduce();
		decisionTable.split();

		{
			final String string = dump( decisionTable);
			assertEquals(
					"YNN A\n" +
					"NYN B\n" +
					"NNY C\n", string);
			logger.debug( string);
		}
	}

	public void testSimple4() {
		final IDecisionTable decisionTable  = new DecisionTable();

		final IExclusiveConditionGroup categoryGroup = new ExclusiveConditionsGroup( "Test");

		categoryGroup.add( new BinaryCondition( "A"));
		categoryGroup.add( new BinaryCondition( "B"));

		decisionTable.add( categoryGroup);
		decisionTable.add( new BinaryCondition( "C"));

		decisionTable.split();

		{
			final String string = dump( decisionTable);
			assertEquals(
					"YYNN A\n" +
					"NNYY B\n" +
					"YNYN C\n", string);
			logger.debug( string);
		}

		decisionTable.reduce();
		decisionTable.split();
		decisionTable.reduce();

		{
			final String string = dump( decisionTable);
			assertEquals(
					"- A\n" +
					"- B\n" +
					"- C\n", string);
			logger.debug( string);
		}
	}

}
