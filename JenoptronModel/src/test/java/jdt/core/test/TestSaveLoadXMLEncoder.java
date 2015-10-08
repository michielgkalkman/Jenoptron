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

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.swing.JFrame;

import jdt.core.DecisionTable;
import jdt.core.binary.BinaryAction;
import jdt.core.binary.BinaryCondition;
import jdt.core.binary.BinaryConditionValue;
import jdt.core.junit.AbstractTestCase;
import jdt.icore.IAction;
import jdt.icore.ICondition;
import jdt.icore.IDecisionTable;
import jdt.util.xmlencoder.XMLEncoderFactory;

import org.apache.commons.lang.SystemUtils;
import org.apache.log4j.Logger;
import org.junit.Test;

public class TestSaveLoadXMLEncoder extends AbstractTestCase {
	public static final Logger logger = Logger.getLogger( TestSaveLoadXMLEncoder.class);

	@Test
	public void testSimple2() {
		final IDecisionTable table = new DecisionTable();

		// Add condition
		{
			final ICondition condition = new BinaryCondition( "yak");
			table.add( condition);
		}

		// Add action
		{
			final IAction action = new BinaryAction( "blub");
			table.add( action);
		}

		// Add action
		{
			final IAction action = new BinaryAction();
			table.add( action);
		}

		table.split();

		{
			final File file = new File( SystemUtils.getJavaIoTmpDir(), "testSimple4.xml");
			final IDecisionTable table2 = (IDecisionTable) testXMLEncoder( file, table);

			logger.debug( dump( table));
			logger.debug( dump( table2));

			logger.debug( table);
			logger.debug( table2);
		}
	}

	@Test
	public void testSimple4() {
		final BinaryConditionValue binaryConditionValue = BinaryConditionValue.NO;

		BinaryConditionValue binaryConditionValue2;
		{
			final File file = new File( SystemUtils.getJavaIoTmpDir(), "testSimple4.xml");
			binaryConditionValue2 = (BinaryConditionValue) testXMLEncoder( file, binaryConditionValue);

			logger.debug( binaryConditionValue);
			logger.debug( binaryConditionValue2);
		}
	}

	@Test
	public void testSimple5() {
		final JFrame frame = new JFrame();
		frame.setBounds( 3, 3, 6, 7);

		{
			final File file = new File( SystemUtils.getJavaIoTmpDir(), "simple5.xml");
			final JFrame frame2 = (JFrame) testXMLEncoder( file, frame);

			logger.debug( frame.getBounds());
			logger.debug( frame2.getBounds());
		}
	}

	@Test
	private Object testXMLEncoder( final File file, final Object object) {
		OutputStream outputStream = null;
		XMLEncoder encoder = null;
		try {
			outputStream = new BufferedOutputStream( new FileOutputStream( file));
			encoder = XMLEncoderFactory.getXMLEncoder( outputStream);

			encoder.writeObject( object);
		} catch (final FileNotFoundException fileNotFoundException) {
			final String errorMsg = "Could not find file";
			logger.error( errorMsg, fileNotFoundException);
		} finally {
			if( encoder != null) {
				encoder.close();
			}
			if( outputStream != null) {
				try {
					outputStream.close();
				} catch ( final IOException exception) {
					final String errorMsg = "Error while closing outputStream";
					logger.error( errorMsg, exception);
				}
			}
		}
		InputStream inputStream = null;
		XMLDecoder decoder = null;
		Object decodedObject = null;
		try {
			inputStream =  new FileInputStream( file);

			decoder = new XMLDecoder( inputStream);

			decodedObject = decoder.readObject();
		} catch (final FileNotFoundException fileNotFoundException) {
			final String errorMsg = "Could not find file";
			logger.error( errorMsg, fileNotFoundException);
		} finally {
			if( decoder != null) {
				decoder.close();
			}
			if( inputStream != null) {
				try {
					inputStream.close();
				} catch ( final IOException exception) {
					final String errorMsg = "Error while closing outputStream";
					logger.error( errorMsg, exception);
				}
			}
		}


		return decodedObject;
	}
}
