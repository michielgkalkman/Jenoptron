package org.taHjaj.wo.jenoptron.generators.spi;

import org.apache.log4j.Logger;
import org.taHjaj.wo.jenoptron.model.junit.AbstractTestCase;

import static org.junit.jupiter.api.Assertions.fail;

public class FactoryTest extends AbstractTestCase {
	private static final Logger logger = Logger.getLogger( FactoryTest.class);

	public void testFactory() {
		for( final Generator generator : GeneratorFactorySPI.getAllGenerators()) {
			
			logger.debug( generator.getShortDescription());
		}
		
		final TextGenerator textGenerator = GeneratorFactorySPI.getTextGenerator( "ASCII");
		
		if( textGenerator == null) {
			fail( "Generator for ASCII should have been present");
		}
	}
}
