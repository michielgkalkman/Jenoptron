package jdt.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import jdt.core.binary.BinaryAction;
import jdt.icore.IAction;
import jdt.icore.ICondition;
import jdt.icore.IConditionValue;
import jdt.icore.IRule;
import jdt.icore.IValue;

/**
 * 
 * @author Michiel Kalkman
 * 
 */
public class Rule extends Model implements IRule {
	/**
	 *
	 */
	private static final long serialVersionUID = -5076191126313606983L;
	private final Map<IAction, IValue> actions;
	private final Map<ICondition, IConditionValue> conditions;

	// Een Rule weet alleen maar welke waarde voor een conditie / actie van
	// toepassing is !!!
	// De table weet de volgorde van de elementen !!

	public Rule() {
		this(new HashMap<IAction, IValue>(), new HashMap<ICondition, IConditionValue>());
	}

	private Rule(final Map<IAction, IValue> actions, final Map<ICondition, IConditionValue> conditions) {
		super();

		this.actions = actions;
		this.conditions = conditions;
	}

	public Rule(final Map<ICondition, IConditionValue> c2v) {
		this(new HashMap<IAction, IValue>(), c2v);
	}

	@Override
	public IRule deepcopy() {
		final Rule rule = new Rule();

		for (final ICondition condition : conditions.keySet()) {
			rule.addCondition(condition.deepcopy(), conditions.get(condition));
		}

		for (final IAction action : actions.keySet()) {
			rule.addAction(action.deepcopy(), actions.get(action));
		}

		return rule;
	}

	@Override
	public final IRule addAction(final IAction action) {
		return addAction(action, action.getUnknownValue());
	}

	@Override
	public final IRule addActions(final Collection<? extends IAction> actions, final IValue value) {
		IRule newRule = this;
		if (actions.isEmpty()) {
			newRule = this;
		} else {
			for (final IAction action : actions) {
				newRule = newRule.addAction(action, value);
			}
		}
		return newRule;
	}

	@Override
	public final IRule addActions(final Collection<? extends IAction> actions) {
		IRule newRule = this;
		if (actions.isEmpty()) {
			newRule = this;
		} else {
			for (final IAction action : actions) {
				newRule = newRule.addAction(action);
			}
		}
		return newRule;
	}

	@Override
	public final IRule addActions(final IValue value, final IAction... actions) {
		IRule newRule = null;
		for (final IAction action : actions) {
			newRule = addAction(action, value);
		}
		return newRule;
	}

	@Override
	public IRule addAction(final IAction action, final IValue value) {
		IRule newRule;
		if (actions.containsKey(action)) {
			newRule = this;
		} else {
			final Map<IAction, IValue> newActions = new HashMap<>();
			newActions.putAll(actions);
			newActions.put(action, value);
			newRule = new Rule(newActions, conditions);
			fire();
		}
		return newRule;
	}

	private void fire() {
		// TODO Auto-generated method stub

	}

	@Override
	public final IRule addCondition(final ICondition condition) {
		return addCondition(condition, condition.getIrrelevantValue());
	}

	@Override
	public IRule addCondition(final ICondition condition, final IConditionValue conditionValue) {
		IRule newRule;
		if (conditions.containsKey(condition)) {
			newRule = this;
		} else {
			final Map<ICondition, IConditionValue> newConditions = new HashMap<>();
			newConditions.putAll(conditions);
			newConditions.put(condition, conditionValue);
			newRule = new Rule(actions, newConditions);
			fire();
		}
		return newRule;
	}

	@Override
	public IConditionValue getConditionValue(final ICondition condition) {
		return conditions.get(condition);
	}

	@Override
	public IRule setConditionValue(final ICondition condition, final IConditionValue conditionValue) {
		IRule newRule;
		if (conditions.containsKey(condition) && conditions.get(condition).equals(conditionValue)) {
			newRule = this;
		} else {
			final Map<ICondition, IConditionValue> newConditions = new HashMap<>();
			newConditions.putAll(conditions);
			newConditions.put(condition, conditionValue);
			newRule = new Rule(actions, newConditions);
			fire();
		}
		return newRule;
	}

	@Override
	public IValue getActionValue(final IAction action) {
		return actions.get(action);
	}

	@Override
	public IRule setActionValue(final IAction action, final IValue value) {
		actions.put(action, value);

		fire();

		return this;
	}

	@Override
	public boolean isInstanceOf(final IRule rule) {
		boolean fIsInstanceOf = true;

		for (final ICondition condition : conditions.keySet()) {
			fIsInstanceOf = conditions.get(condition).isInstanceOf(rule.getConditionValue(condition));
			if (!fIsInstanceOf) {
				break;
			}
		}

		return fIsInstanceOf;
	}

	@Override
	public IRule setConditionValues(final Map<ICondition, IConditionValue> values) {
		for (final Entry<ICondition, IConditionValue> entry : values.entrySet()) {
			conditions.put(entry.getKey(), entry.getValue());
		}
		fire();

		return this;
	}

	@Override
	public String toString() {
		final StringBuffer stringBuffer = new StringBuffer();
		boolean fFirst = true;

		for (final ICondition condition : getConditions().keySet()) {
			if (fFirst) {
				stringBuffer.append(condition.getShortDescription()).append(':')
						.append(StringUtils.center(conditions.get(condition).toString(), 3));
				fFirst = false;
			} else {
				stringBuffer.append(',').append(condition.getShortDescription()).append(':')
						.append(StringUtils.center(conditions.get(condition).toString(), 3));
			}
		}

		fFirst = true;

		stringBuffer.append("-->");

		for (final IAction action : getActions().keySet()) {
			if (fFirst) {
				stringBuffer.append(action.getShortDescription()).append(':')
						.append(StringUtils.center(actions.get(action).toString(), 4));
			} else {
				stringBuffer.append(',').append(action.getShortDescription()).append(':')
						.append(StringUtils.center(actions.get(action).toString(), 4));
			}
		}

		return stringBuffer.toString();
	}

	@Override
	public boolean equals(final Object obj) {
		return compareTo(obj) == 0;
	}

	/**
	 * Do not use for sorting - sorting can only be done with RuleComparator
	 * which takes into account the requuired order of conditions.
	 * 
	 * @see jdt.core.RuleComparator
	 * 
	 * @param object
	 * @return
	 */
	@Override
	public int compareTo(final Object object) {
		int result = -1;

		if (object instanceof Rule) {
			final Rule rule = (Rule) object;

			result = 0;

			{
				final int nrConditions = getConditions().size();
				final int nrConditionsRule = rule.getConditions().size();

				if (nrConditions < nrConditionsRule) {
					result = -1;
				} else if (nrConditions == nrConditionsRule) {
					final int nrActions = getActions().size();
					final int nrActionsRule = rule.getActions().size();

					if (nrActions < nrActionsRule) {
						result = -1;
					} else if (nrActions > nrActionsRule) {
						result = 1;
					}
				} else {
					result = 1;
				}
			}

			if (result == 0) {
				for (final ICondition condition : conditions.keySet()) {
					result = getConditionValue(condition).compareTo(rule.getConditionValue(condition));
					if (result != 0) {
						break;
					}
				}
			}

			if (result == 0) {
				for (final IAction action : actions.keySet()) {
					result = getActionValue(action).compareTo(rule.getActionValue(action));
					if (result != 0) {
						break;
					}
				}
			}
		}

		return result;
	}

	@Override
	public Map<IAction, IValue> getActions() {
		return actions;
	}

	@Override
	public Map<ICondition, IConditionValue> getConditions() {
		return conditions;
	}

	public void setActions(final Map<IAction, IValue> actions) {
		this.actions.putAll(actions);
		fire();
	}

	public void setConditions(final Map<ICondition, IConditionValue> conditions) {
		this.conditions.putAll(conditions);
		fire();
	}

	@Override
	public IRule remove(final IAction action) {
		final Map<IAction, IValue> newActions = new HashMap<>();

		newActions.putAll(actions);
		newActions.remove(action);
		fire();

		return new Rule(newActions, conditions);
	}

	@Override
	public void remove(final ICondition condition) {
		conditions.remove(condition);
		fire();
	}

	@Override
	public IRule setActionValue(final String action, final IValue value) {
		return setActionValue(new BinaryAction(action), value);
	}

	@Override
	public IRule addConditions(final List<ICondition> newTableConditions) {
		IRule newRule = null;
		if (newTableConditions.isEmpty()) {
			newRule = this;
		} else {
			newRule = this;
			for (final ICondition condition : newTableConditions) {
				newRule = newRule.addCondition(condition);
			}
		}
		return newRule;
	}

	@Override
	public IRule add(final Map<ICondition, IConditionValue> c2v) {
		IRule newRule = null;
		if (c2v.isEmpty()) {
			newRule = this;
		} else {
			final Map<ICondition, IConditionValue> newConditions = new HashMap<>();
			newConditions.putAll(conditions);
			newConditions.putAll(c2v);
			newRule = new Rule(actions, newConditions);
			fire();
		}
		return newRule;
	}

	@Override
	public IRule addActions(final Map<IAction, IValue> map) {
		IRule newRule = null;
		if (map.isEmpty()) {
			newRule = this;
		} else {
			final Map<IAction, IValue> newActions = new HashMap<>();
			newActions.putAll(actions);
			newActions.putAll(map);
			newRule = new Rule(newActions, conditions);
			fire();
		}
		return newRule;
	}
}
