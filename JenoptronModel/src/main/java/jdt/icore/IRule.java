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
