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
package jdt.icore;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface IRule extends IObject, Comparable {
	IConditionValue getConditionValue(final ICondition condition);

	IValue getActionValue(final IAction action);

	IRule setActionValue(final IAction action, IValue value);

	IRule setActionValue(final String action, IValue value);

	// void setActionValue(Map<IAction,IValue> values);
	IRule setConditionValue(ICondition condition, IConditionValue conditionValue);

	IRule setConditionValues(Map<ICondition, IConditionValue> values);

	Map<ICondition, IConditionValue> getConditions();

	Map<IAction, IValue> getActions();

	IRule addAction(final IAction action);

	IRule addAction(final IAction action, IValue value);

	IRule addCondition(final ICondition condition);

	IRule addCondition(final ICondition condition, IConditionValue conditionValue);

	IRule remove(final IAction action);

	void remove(final ICondition condition);

	boolean isInstanceOf(IRule rule);

	IRule addActions(Collection<? extends IAction> actions, IValue defaultActionValue);

	IRule addActions(IValue defaultActionValue, IAction... actions);

	IRule addActions(Collection<? extends IAction> actions);

	IRule addConditions(List<ICondition> newTableConditions);

	IRule add(Map<ICondition, IConditionValue> c2v);

	IRule addActions(Map<IAction, IValue> map);
}
