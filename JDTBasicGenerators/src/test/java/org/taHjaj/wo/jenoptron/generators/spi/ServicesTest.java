package org.taHjaj.wo.jenoptron.generators.spi;

import org.apache.log4j.Logger;
import org.taHjaj.wo.jenoptron.model.core.DecisionTable;
import org.taHjaj.wo.jenoptron.model.core.binary.BinaryAction;
import org.taHjaj.wo.jenoptron.model.core.binary.BinaryCondition;
import org.taHjaj.wo.jenoptron.model.icore.IDecisionTable;
import org.taHjaj.wo.jenoptron.model.junit.AbstractTestCase;

import java.util.ServiceLoader;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class ServicesTest extends AbstractTestCase {
	private static final Logger logger = Logger.getLogger( ServicesTest.class);

	public void testSimple() {
		final ServiceLoader<GeneratorFactorySPI> generatorFactories
			= ServiceLoader.load(GeneratorFactorySPI.class);

		assertTrue(generatorFactories.iterator().hasNext(), "Expecting at least one SPI Factory");

		for( final GeneratorFactorySPI generatorFactory : generatorFactories) {
			assertNotNull(
					generatorFactory.getGenerators(), "Found SPI factory, expecting at least one SPI Implementation");

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
		assertNotNull(
				GeneratorFactorySPI.getAnyTextGenerator(), "At least one ascii generator should be available");
	}
}
