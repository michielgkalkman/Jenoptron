package jdt.core.category;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import jdt.icore.IAction;
import jdt.icore.ICondition;
import jdt.icore.IRule;
import jdt.icore.IValue;

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

		actions = new HashMap<IAction, IValue>();
		conditionalActions = new HashMap<IAction, IValue>();
		conditions = new HashMap<ICondition, IValue>();
		impliedActions = new HashMap<IAction, IValue>();
		impliedConditions = new HashMap<ICondition, IValue>();
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

		if (fConditionsHold) {
			for (final IAction action : conditionalActions.keySet()) {
				final IValue value = rule.getActionValue(action);
				if ((value == null) || !conditionalActions.get(action).equals(value)) {
					fConditionalActionsHold = false;
					break;
				}
			}
		}

		boolean fActionsHold = true;

		if (fConditionsHold && fConditionalActionsHold) {
			for (final IAction action : conditionalActions.keySet()) {
				final IValue value = rule.getActionValue(action);
				if ((value == null) || !conditionalActions.get(action).equals(value)) {
					fActionsHold = false;
					break;
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
		final List<ICondition> list = new ArrayList<ICondition>();
		list.addAll(conditions.keySet());
		list.addAll(impliedConditions.keySet());

		return Collections.unmodifiableList(list);
	}

	@Override
	public List<IAction> actions() {
		final List<IAction> list = new ArrayList<IAction>();
		list.addAll(actions.keySet());
		list.addAll(impliedActions.keySet());

		return Collections.unmodifiableList(list);
	}

	@Override
	public String toString() {
		final StringBuffer stringBuffer = new StringBuffer();
		boolean fFirst = true;

		for (final ICondition condition : this.conditions.keySet()) {
			if (fFirst) {
				stringBuffer.append(condition.getShortDescription()).append(':')
						.append(StringUtils.center(conditions.get(condition).toString(), 3));
				fFirst = false;
			} else {
				stringBuffer.append(',').append(condition.getShortDescription()).append(':')
						.append(StringUtils.center(conditions.get(condition).toString(), 3));
			}
		}

		for (final IAction action : actions.keySet()) {
			if (fFirst) {
				stringBuffer.append(action.getShortDescription()).append(':')
						.append(StringUtils.center(actions.get(action).toString(), 4));
			} else {
				stringBuffer.append(',').append(action.getShortDescription()).append(':')
						.append(StringUtils.center(actions.get(action).toString(), 4));
			}
		}

		fFirst = true;

		stringBuffer.append("-->");

		for (final ICondition condition : this.impliedConditions.keySet()) {
			if (fFirst) {
				stringBuffer.append(condition.getShortDescription()).append(':')
						.append(StringUtils.center(impliedConditions.get(condition).toString(), 3));
				fFirst = false;
			} else {
				stringBuffer.append(',').append(condition.getShortDescription()).append(':')
						.append(StringUtils.center(impliedConditions.get(condition).toString(), 3));
			}
		}

		for (final IAction action : impliedActions.keySet()) {
			if (fFirst) {
				stringBuffer.append(action.getShortDescription()).append(':')
						.append(StringUtils.center(impliedActions.get(action).toString(), 4));
			} else {
				stringBuffer.append(',').append(action.getShortDescription()).append(':')
						.append(StringUtils.center(impliedActions.get(action).toString(), 4));
			}
		}

		return stringBuffer.toString();
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
