package jdt.icore;

import java.util.Map;

import com.google.common.eventbus.EventBus;

public interface IRule extends IObject, Observable, Comparable {
	IRule deepcopy();

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

	IRule addCondition(final ICondition condition,
			IConditionValue conditionValue);

	void remove(final IAction action);

	void remove(final ICondition condition);

	boolean isInstanceOf(IRule rule);

	void addPropertyChangeListener(EventBus eventBus);
}
