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
package org.taHjaj.wo.jenoptron.model.core.category;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import org.taHjaj.wo.jenoptron.model.core.binary.BinaryConditionValue;
import org.taHjaj.wo.jenoptron.model.icore.IAction;
import org.taHjaj.wo.jenoptron.model.icore.ICondition;
import org.taHjaj.wo.jenoptron.model.icore.IRule;

public class ExclusiveConditionsGroup implements IExclusiveConditionGroup {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2141178139072446295L;
	private final List<ICondition> mutualExclusiveConditions;
	private final String shortDescription;

	public ExclusiveConditionsGroup(final String shortDescription) {
		this(shortDescription, null);
	}

	public ExclusiveConditionsGroup(final String shortDescription, final List<ICondition> mutualExclusiveConditions) {
		super();

		this.shortDescription = shortDescription;
		this.mutualExclusiveConditions = mutualExclusiveConditions;
	}

	@Override
	public ExclusiveConditionsGroup add(final ICondition... conditions) {
		final List<ICondition> newMutualExclusiveConditions = new ArrayList<>();
		if (mutualExclusiveConditions != null) {
			newMutualExclusiveConditions.addAll(mutualExclusiveConditions);
		}
		Collections.addAll(newMutualExclusiveConditions, conditions);
		return new ExclusiveConditionsGroup(shortDescription, newMutualExclusiveConditions);
	}

	@Override
	public boolean isValid(final IRule rule) {
		int countYes = 0;
		int countIrrelevant = 0;

		for (final ICondition condition : mutualExclusiveConditions) {
			if (rule.getConditionValue(condition).equals(BinaryConditionValue.YES)) {
				countYes++;
			} else if (rule.getConditionValue(condition).equals(BinaryConditionValue.IRRELEVANT)) {
				countIrrelevant++;
			}
		}

		return countYes == 1 || countIrrelevant == mutualExclusiveConditions.size();
	}

	@Override
	public List<ICondition> conditions() {
		return mutualExclusiveConditions;
	}

	@Override
	public List<IAction> actions() {
		return Collections.EMPTY_LIST;
	}

	public void setActionValues(final IRule rule) {
		// No actions, so no action here.
	}

	@Override
	public String getShortDescription() {
		return shortDescription;
	}

	public ExclusiveConditionsGroup setShortDescription(final String shortDescription) {
		final ExclusiveConditionsGroup exclusiveConditionsGroup;
		if (StringUtils.equals(shortDescription, this.shortDescription)) {
			exclusiveConditionsGroup = this;
		} else {
			exclusiveConditionsGroup = new ExclusiveConditionsGroup(shortDescription, mutualExclusiveConditions);
		}
		return exclusiveConditionsGroup;
	}

	@Override
	public String getShortDescription(final String groupMemberShortDescription) {
		return getShortDescription() + ":" + groupMemberShortDescription;
	}

	@Override
	public void toString(StringBuilder stringBuilder) {
		stringBuilder.append(toString());
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + '(' + getShortDescription() + "):"
				+ StringUtils.join(mutualExclusiveConditions, ',');
	}
}
