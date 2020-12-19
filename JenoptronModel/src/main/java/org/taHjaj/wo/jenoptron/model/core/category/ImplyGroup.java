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

import java.util.*;

import org.apache.commons.lang.StringUtils;

import org.taHjaj.wo.jenoptron.model.icore.IAction;
import org.taHjaj.wo.jenoptron.model.icore.ICondition;
import org.taHjaj.wo.jenoptron.model.icore.IRule;
import org.taHjaj.wo.jenoptron.model.icore.IValue;

public class ImplyGroup implements IImplyGroup {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8033341772485771658L;
	private String shortDescription;
	private final Map<IAction, IValue> actions;
	private final Map<IAction, IValue> conditionalActions;
	private final Map<ICondition, IValue> conditions;
	private final Map<IAction, IValue> impliedActions;
	private final Map<ICondition, IValue> impliedConditions;

	public ImplyGroup() {
		this("Deze constructor niet gebruiken !");
	}

	public ImplyGroup(final String shortDescription) {
		this.shortDescription = shortDescription;

		actions = new HashMap<>();
		conditionalActions = new HashMap<>();
		conditions = new HashMap<>();
		impliedActions = new HashMap<>();
		impliedConditions = new HashMap<>();
	}

	@Override
	public void add(final IAction action) {
		add(action, action.getDefaultValue());
	}

	@Override
	public void add(final IAction action, final IValue value) {
		conditionalActions.put(action, value);
	}

	@Override
	public void add(final ICondition condition) {
		add(condition, condition.getDefaultValue());
	}

	@Override
	public void add(final ICondition condition, final IValue value) {
		conditions.put(condition, value);
	}

	@Override
	public void implies(final IAction action) {
		implies(action, action.getDefaultValue());
	}

	@Override
	public void implies(final IAction action, final IValue value) {
		impliedActions.put(action, value);
	}

	@Override
	public void implies(final ICondition condition) {
		implies(condition, condition.getDefaultValue());
	}

	@Override
	public void implies(final ICondition condition, final IValue value) {
		impliedConditions.put(condition, value);
	}

	private boolean implies(final IRule rule) {
		boolean fConditionsHold = true;

		for (final ICondition condition : conditions.keySet()) {
			final IValue value = rule.getConditionValue(condition);
			if ((value == null) || !conditions.get(condition).equals(value)) {
				fConditionsHold = false;
				break;
			}
		}

		boolean fConditionalActionsHold = true;
		boolean fActionsHold = true;


		if (fConditionsHold) {
			final Set<IAction> iActions = conditionalActions.keySet();
			for (final IAction action : iActions) {
				final IValue value = rule.getActionValue(action);
				if ((value == null) || !conditionalActions.get(action).equals(value)) {
					fConditionalActionsHold = false;
					break;
				}
			}

			if (fConditionalActionsHold) {
				for (final IAction action : iActions) {
					final IValue value = rule.getActionValue(action);
					if ((value == null) || !conditionalActions.get(action).equals(value)) {
						fActionsHold = false;
						break;
					}
				}
			}
		}


		return fConditionsHold && fConditionalActionsHold && fActionsHold;
	}

	@Override
	public boolean isValid(final IRule rule) {
		final boolean fImplied = implies(rule);
		boolean fImpliedConditionsHold = true;
		boolean fImpliedActionsHold = true;

		if (fImplied) {
			for (final ICondition condition : impliedConditions.keySet()) {
				final IValue value = rule.getConditionValue(condition);
				if ((value != null) && !impliedConditions.get(condition).equals(value)) {
					fImpliedConditionsHold = false;
					break;
				}
			}

			if (fImpliedConditionsHold) {
				for (final IAction action : impliedActions.keySet()) {
					final IValue value = rule.getActionValue(action);
					if ((value != null) && !impliedActions.get(action).equals(value)) {
						fImpliedActionsHold = false;
						break;
					}
				}
			}
		}

		return !(fImplied) || (fImpliedConditionsHold && fImpliedActionsHold);
	}

	@Override
	public List<ICondition> conditions() {
		final List<ICondition> list = new ArrayList<>();
		list.addAll(conditions.keySet());
		list.addAll(impliedConditions.keySet());

		return Collections.unmodifiableList(list);
	}

	@Override
	public List<IAction> actions() {
		final List<IAction> list = new ArrayList<>();
		list.addAll(actions.keySet());
		list.addAll(impliedActions.keySet());

		return Collections.unmodifiableList(list);
	}

	@Override
	public String toString() {
		final StringBuilder stringBuilder = new StringBuilder();
		toString(stringBuilder);

		return stringBuilder.toString();
	}

	@Override
	public void toString(StringBuilder stringBuilder) {
		boolean fFirst = true;

		for (final ICondition condition : this.conditions.keySet()) {
			final String conditionShortDescription = condition.getShortDescription();
			final String conditionString = conditions.get(condition).toString();
			if (fFirst) {
				stringBuilder.append(conditionShortDescription).append(':')
						.append(StringUtils.center(conditionString, 3));
				fFirst = false;
			} else {
				stringBuilder.append(',').append(conditionShortDescription).append(':')
						.append(StringUtils.center(conditionString, 3));
			}
		}

		for (final IAction action : actions.keySet()) {
			final String actionShortDescription = action.getShortDescription();
			if (fFirst) {
				stringBuilder.append(actionShortDescription).append(':')
						.append(StringUtils.center(actions.get(action).toString(), 4));
			} else {
				stringBuilder.append(',').append(actionShortDescription).append(':')
						.append(StringUtils.center(actions.get(action).toString(), 4));
			}
		}

		fFirst = true;

		stringBuilder.append("-->");

		for (final ICondition condition : this.impliedConditions.keySet()) {
			if (fFirst) {
				stringBuilder.append(condition.getShortDescription()).append(':')
						.append(StringUtils.center(impliedConditions.get(condition).toString(), 3));
				fFirst = false;
			} else {
				stringBuilder.append(',').append(condition.getShortDescription()).append(':')
						.append(StringUtils.center(impliedConditions.get(condition).toString(), 3));
			}
		}

		for (final IAction action : impliedActions.keySet()) {
			if (fFirst) {
				stringBuilder.append(action.getShortDescription()).append(':')
						.append(StringUtils.center(impliedActions.get(action).toString(), 4));
			} else {
				stringBuilder.append(',').append(action.getShortDescription()).append(':')
						.append(StringUtils.center(impliedActions.get(action).toString(), 4));
			}
		}
	}

	@Override
	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(final String shortDescription) {
		this.shortDescription = shortDescription;
	}

	@Override
	public String getShortDescription(final String groupMemberShortDescription) {
		return getShortDescription() + ":" + groupMemberShortDescription;
	}
}
