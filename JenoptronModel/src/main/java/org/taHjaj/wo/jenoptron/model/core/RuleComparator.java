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
package org.taHjaj.wo.jenoptron.model.core;

import java.io.Serializable;
import java.util.Comparator;
import java.util.List;

import org.taHjaj.wo.jenoptron.model.icore.ICondition;
import org.taHjaj.wo.jenoptron.model.icore.IRule;

public class RuleComparator implements Comparator<IRule>, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2435669279524367671L;
	private final List<ICondition> conditions;

	public RuleComparator(final List<ICondition> conditions) {
		this.conditions = conditions;
	}

	@Override
	public int compare(final IRule rule1, final IRule rule2) {
		int result = 0;

		{
			final int nrConditions1 = rule1.getConditions().size();
			final int nrConditions2 = rule2.getConditions().size();

			if (nrConditions1 < nrConditions2) {
				result = -1;
			} else if (nrConditions1 == nrConditions2) {
				final int nrActions1 = rule1.getActions().size();
				final int nrActions2 = rule2.getActions().size();

				if (nrActions1 < nrActions2) {
					result = -1;
				} else if (nrActions1 > nrActions2) {
					result = 1;
				}
			} else {
				result = 1;
			}
		}

		if (result == 0) {
			for (final ICondition condition : conditions) {
				result = rule1.getConditionValue(condition).compareTo(rule2.getConditionValue(condition));
				if (result != 0) {
					break;
				}
			}
		}

		return result;
	}
}
