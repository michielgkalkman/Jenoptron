package jdt.core.services.test;
import jdt.core.junit.AbstractTestCase;

import org.apache.log4j.Logger;

import dt.generators.Generator;
import dt.generators.GeneratorFactorySPI;
import dt.generators.TextGenerator;


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
