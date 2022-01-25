package org.taHjaj.wo.jenoptron.generators.spi;

import org.apache.log4j.Logger;
import org.taHjaj.wo.jenoptron.generators.spi.ascii.AsciiTableOptions;
import org.taHjaj.wo.jenoptron.model.core.DecisionTable;
import org.taHjaj.wo.jenoptron.model.core.binary.BinaryAction;
import org.taHjaj.wo.jenoptron.model.core.binary.BinaryCondition;
import org.taHjaj.wo.jenoptron.model.icore.IAction;
import org.taHjaj.wo.jenoptron.model.icore.ICondition;
import org.taHjaj.wo.jenoptron.model.icore.IDecisionTable;
import org.taHjaj.wo.jenoptron.model.junit.AbstractTestCase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class AsciiGeneratorTest extends AbstractTestCase {
	private static final Logger logger = Logger.getLogger( AsciiGeneratorTest.class);

	public void testFactory() {
		for( final Generator generator : GeneratorFactorySPI.getAllGenerators()) {
			logger.debug( generator.getShortDescription());
		}
		
		final TextGenerator textGenerator = GeneratorFactorySPI.getTextGenerator( "ASCII");
		
		if( textGenerator == null) {
			fail( "Generator for ASCII should have been present");
		}
		
		final GeneratorOptions generatorOptions = textGenerator.getGeneratorOptions();

		logger.debug( generatorOptions);
		
		final AsciiTableOptions asciiTableOptions = (AsciiTableOptions) generatorOptions;
		
		asciiTableOptions.setFBorders(true);
		asciiTableOptions.setFWrapping(true);
		asciiTableOptions.setFConditionActionSeparator(true);
		
		logger.debug( asciiTableOptions);
		
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
				"+-+-+-+-+-+\n" +
				"|A|Y|Y|N|N|\n" +
				"+-+-+-+-+-+\n" +
				"|B|Y|N|Y|N|\n" +
				"===========\n" +
				"|X|?|?|?|?|\n" +
				"+-+-+-+-+-+\n", table);

		logger.debug( table);
	}
}
