/*******************************************************************************
 * Copyright 2015 Michiel Kalkman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
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
