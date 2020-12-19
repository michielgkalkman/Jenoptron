/*
 * ******************************************************************************
 *  * Copyright 2020 Michiel Kalkman
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *     http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  ******************************************************************************
 */
package org.taHjaj.wo.jenoptron.model.xstream;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.taHjaj.wo.jenoptron.model.icore.IDecisionTable;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

import com.thoughtworks.xstream.XStream;

public class IO {
	private static final Logger logger = Logger.getLogger( IO.class);
	
	private IO() {
		super();
	}
	
	private static final XStream xStream = getXStream();
//	
//	static {
//		xStream = new XStream();
//		
//		xStream.alias( "decisionTable", DecisionTable.class);
//		xStream.alias( "rule", Rule.class);
//		xStream.alias( "action", BinaryAction.class);
//		xStream.alias( "condition", BinaryCondition.class);
//		xStream.alias( "actionValue", BinaryActionValue.class);
//		xStream.alias( "conditionValue", BinaryConditionValue.class);
//		xStream.alias( "implyGroup", ImplyGroup.class);
//	}
	
	private static XStream getXStream() {
		final XStream xStream = new XStream();
		
//		xStream.aliasType( "decisiontable", DecisionTable.class);
//		xStream.alias( "rule", IRule.class);
//		xStream.alias( "action", IAction.class);
//		xStream.alias( "condition", ICondition.class);
//		xStream.alias( "actionValue", IValue.class);
//		xStream.alias( "conditionValue", IConditionValue.class);
//		xStream.alias( "implyGroup", IImplyGroup.class);
		
		return xStream;
	}
	
	public static void save( final String file, final IDecisionTable decisionTable) {
		final String xmlString = xStream.toXML( decisionTable);
		
		logger.debug( xmlString);
	}
	
	public static String toXML( final IDecisionTable decisionTable) {
		return xStream.toXML( decisionTable);
	}
	
	public static IDecisionTable fromXML( final String xmlString) {
		final XStream stream = new XStream();
		
		return (IDecisionTable)stream.fromXML( xmlString);
	}

	public static IDecisionTable fromXML( final File file) 
			throws IOException {
		final XStream stream = new XStream();
		
		String xmlString;
		
		try {
			xmlString = FileUtils.readFileToString( file, Charset.defaultCharset());
		} catch ( final IOException exception) {
			final String errorMsg = "Could not read the contents of the file"
				+ file.getAbsolutePath()
				+ " to string.";
			logger.error( errorMsg, exception);
			throw exception;
		}

		return (IDecisionTable)stream.fromXML( xmlString);
	}

	public static IDecisionTable load( final String file) {
		return null;
	}
}
