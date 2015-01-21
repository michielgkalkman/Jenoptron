package jdt.core.services.test;

import java.util.ServiceLoader;

import jdt.core.DecisionTable;
import jdt.core.binary.BinaryAction;
import jdt.core.binary.BinaryCondition;
import jdt.core.junit.AbstractTestCase;
import jdt.icore.IDecisionTable;

import org.apache.log4j.Logger;

import dt.generators.Generator;
import dt.generators.GeneratorFactorySPI;
import dt.generators.TextGenerator;

public class ServicesTest extends AbstractTestCase {
	private static final Logger logger = Logger.getLogger( ServicesTest.class);

	public void testSimple() {
		final ServiceLoader<GeneratorFactorySPI> generatorFactories
			= ServiceLoader.load(GeneratorFactorySPI.class);

		assertEquals( "Expecting at least one SPI Factory", true, generatorFactories.iterator().hasNext());

		for( final GeneratorFactorySPI generatorFactory : generatorFactories) {
			assertNotNull( "Found SPI factory, expecting at least one SPI Implementation",
					generatorFactory.getGenerators());

			for( final Generator generator : generatorFactory.getGenerators()) {
				logger.debug( generator.getShortDescription());
				
				logger.debug( generator.getSuffix());
			}

			final IDecisionTable decisionTable = new DecisionTable();
			decisionTable.add( new BinaryCondition( "x"));
			decisionTable.add( new BinaryAction( "x"));
			
			for( final TextGenerator textGenerator : GeneratorFactorySPI.getTextGenerators()) {
				logger.debug( textGenerator.getText( decisionTable));
			}
		}
	}

	public void testGetAllGenerators() {
		GeneratorFactorySPI.getAllGenerators();
	}

	public void testGetAnyGenerator() {
		assertNotNull( "At least one ascii generator should be available",
				GeneratorFactorySPI.getAnyTextGenerator());
	}
}
