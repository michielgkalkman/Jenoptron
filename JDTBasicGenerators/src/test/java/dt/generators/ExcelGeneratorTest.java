package dt.generators;

import jdt.core.DecisionTable;
import jdt.core.binary.BinaryAction;
import jdt.core.binary.BinaryCondition;
import jdt.core.generators.junit.AbstractGeneratorTestCase;
import jdt.icore.IAction;
import jdt.icore.ICondition;
import jdt.icore.IDecisionTable;
import org.apache.log4j.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class ExcelGeneratorTest extends AbstractGeneratorTestCase {
	private static final Logger logger = Logger.getLogger( ExcelGeneratorTest.class);

	public void testFactory() {
		for( final Generator generator : GeneratorFactorySPI.getAllGenerators()) {
			
			logger.debug( generator.getShortDescription());
		}
		
		final TextGenerator textGenerator = GeneratorFactorySPI.getTextGenerator( "Excel");
		
		if( textGenerator == null) {
			fail( "Generator for Excel should have been present");
		}
		
		final ICondition A = new BinaryCondition( "A");
		final ICondition B = new BinaryCondition( "B");
		final IAction action = new BinaryAction( "X");

		final IDecisionTable decisionTable = new DecisionTable()
				.add( action)
				.add( A)
				.add( B)
				.split();
		
		final String table = textGenerator.getText(decisionTable);
		assertEquals(
				"A\tYes\tYes\tNo\tNo\n" +
				"B\tYes\tNo\tYes\tNo\n" +
				"X\t?\t?\t?\t?\n", table);

		logger.debug( table);
	}
}
