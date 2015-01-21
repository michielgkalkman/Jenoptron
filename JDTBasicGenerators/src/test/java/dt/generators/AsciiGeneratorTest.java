package dt.generators;
import jdt.core.DecisionTable;
import jdt.core.binary.BinaryAction;
import jdt.core.binary.BinaryCondition;
import jdt.core.junit.AbstractTestCase;
import jdt.icore.IAction;
import jdt.icore.ICondition;
import jdt.icore.IDecisionTable;

import org.apache.log4j.Logger;

import dt.generators.ascii.AsciiTableOptions;

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
		
		final IDecisionTable decisionTable = new DecisionTable();

		final ICondition A = new BinaryCondition( "A");
		final ICondition B = new BinaryCondition( "B");
		final IAction action = new BinaryAction( "X");
		decisionTable.add( action);
		decisionTable.add( A);
		decisionTable.add( B);
		
		decisionTable.split();
		
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
