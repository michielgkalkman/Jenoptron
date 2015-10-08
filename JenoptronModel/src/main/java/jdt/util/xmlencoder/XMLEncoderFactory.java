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
package jdt.util.xmlencoder;

import java.beans.ExceptionListener;
import java.beans.PersistenceDelegate;
import java.beans.XMLEncoder;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class XMLEncoderFactory {
	private final Map<Class, PersistenceDelegate> persistenceDelegates;
	private static XMLEncoderFactory encoderFactory;
	
	private XMLEncoderFactory() {
		persistenceDelegates = new HashMap<Class, PersistenceDelegate>();
	}

	private synchronized static XMLEncoderFactory getXMLEncoderFactory() {
		if( encoderFactory == null) {
			encoderFactory = new XMLEncoderFactory();
		}			
		return encoderFactory;
	}
	
	private static Map<Class, PersistenceDelegate> getPersistenceDelegates() {
		return getXMLEncoderFactory().persistenceDelegates;
	}
	
	public static XMLEncoder getXMLEncoder( final OutputStream outputStream) {
		final XMLEncoder encoder = new XMLEncoder( outputStream);
	
		encoder.setExceptionListener(new ExceptionListener() {
		    public void exceptionThrown(final Exception exception) {
		        exception.printStackTrace();
		    }
		});
		
		for( final Entry<Class, PersistenceDelegate> entry : getPersistenceDelegates().entrySet()) {
			encoder.setPersistenceDelegate( entry.getKey(), entry.getValue());
		}
		
		return encoder;
	}
	
	public static void add( final Class someClass, final PersistenceDelegate persistenceDelegate) {
		getPersistenceDelegates().put( someClass, persistenceDelegate);
		
	}
}
