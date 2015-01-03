package jdt.core.test;

import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.junit.Test;

import jdt.core.junit.AbstractTestCase;

public class TestEnv extends AbstractTestCase {
	private static final Logger logger = Logger.getLogger( TestEnv.class);
	
	@Test
	public void testDisplay() {
		for( final Entry<String, String> entry : System.getenv().entrySet()) {
			logger.debug( entry.getKey() + " --> " + entry.getValue());
		}
		
		for( final Entry<Object, Object> entry : System.getProperties().entrySet()) {
			logger.debug( entry.getKey() + " --> " + entry.getValue());
		}
		
	}
}
