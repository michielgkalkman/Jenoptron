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
package org.taHjaj.wo.jenoptron.model.junit;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.taHjaj.wo.jenoptron.model.core.binary.BinaryCondition;
import org.taHjaj.wo.jenoptron.model.icore.ICondition;
import org.taHjaj.wo.jenoptron.model.icore.IDecisionTable;

import org.apache.log4j.BasicConfigurator;

public abstract class AbstractTestCase {

	@BeforeAll
	static void setUp() {
		BasicConfigurator.configure();
	}

	@AfterAll
	static void tearDown() {
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
