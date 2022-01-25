package org.taHjaj.wo.jenoptron.generators.spi;

import org.apache.log4j.Logger;
import org.taHjaj.wo.jenoptron.generators.junit.AbstractGeneratorTestCase;
import org.taHjaj.wo.jenoptron.model.core.DecisionTable;
import org.taHjaj.wo.jenoptron.model.core.binary.BinaryAction;
import org.taHjaj.wo.jenoptron.model.core.binary.BinaryCondition;
import org.taHjaj.wo.jenoptron.model.icore.IAction;
import org.taHjaj.wo.jenoptron.model.icore.ICondition;
import org.taHjaj.wo.jenoptron.model.icore.IDecisionTable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
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
