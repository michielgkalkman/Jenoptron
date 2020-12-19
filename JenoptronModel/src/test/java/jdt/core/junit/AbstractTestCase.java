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
package jdt.core.junit;

import java.util.ArrayList;
import java.util.List;

import org.taHjaj.wo.jenoptron.model.core.binary.BinaryCondition;
import org.taHjaj.wo.jenoptron.model.icore.ICondition;
import org.taHjaj.wo.jenoptron.model.icore.IDecisionTable;
import junit.framework.TestCase;

import org.apache.log4j.BasicConfigurator;

public abstract class AbstractTestCase extends TestCase {

	public AbstractTestCase() {
		super();
	}

	public AbstractTestCase(final String arg0) {
		super(arg0);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		BasicConfigurator.configure();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		BasicConfigurator.resetConfiguration();
	}

	protected List<? extends ICondition> getConditionList(final String ... conditions) {
		final List<BinaryCondition> binaryConditions = new ArrayList<BinaryCondition>();
		for( final String condition : conditions) {
			binaryConditions.add( new BinaryCondition( condition));
		}

		return binaryConditions;
	}
	
	protected String dump( final IDecisionTable decisionTable) {
		return decisionTable.simpleDump().toString();
	}
}
