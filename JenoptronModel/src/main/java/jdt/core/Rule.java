package jdt.core;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import jdt.core.binary.BinaryAction;
import jdt.icore.IAction;
import jdt.icore.ICondition;
import jdt.icore.IConditionValue;
import jdt.icore.IRule;
import jdt.icore.IValue;

import org.apache.commons.lang.StringUtils;

import com.google.common.eventbus.EventBus;

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
	private final Map<ICondition, IConditionValue> conditions; // Dit moet een
																// list / map
																// zijn !!!
	private EventBus eventBus;

	// Een Rule weet alleen maar welke waarde voor een conditie / actie van
	// toepassing is !!!
	// De table weet de volgorde van de elementen !!

	public Rule() {
		super();
		actions = new HashMap<IAction, IValue>();
		conditions = new HashMap<ICondition, IConditionValue>();
	}

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

	public final IRule addAction(final IAction action) {
		actions.put(action, action.getUnknownValue());
		fire();
		return this;
	}

	public IRule addAction(final IAction action, final IValue value) {
		actions.put(action, value);
		fire();
		return this;
	}

	private void fire() {
		// TODO Auto-generated method stub

	}

	public final IRule addCondition(final ICondition condition) {
		conditions.put(condition, condition.getIrrelevantValue());
		fire();
		return this;
	}

	public IRule addCondition(final ICondition condition,
			final IConditionValue conditionValue) {
		conditions.put(condition, conditionValue);
		fire();
		return this;
	}

	public IConditionValue getConditionValue(final ICondition condition) {
		return conditions.get(condition);
	}

	public IRule setConditionValue(final ICondition condition,
			final IConditionValue conditionValue) {
		conditions.put(condition, conditionValue);
		fire();

		return this;
	}

	public IValue getActionValue(final IAction action) {
		return actions.get(action);
	}

	public IRule setActionValue(final IAction action, final IValue value) {
		actions.put(action, value);

		fire();

		return this;
	}

	public boolean isInstanceOf(final IRule rule) {
		boolean fIsInstanceOf = true;

		for (final ICondition condition : conditions.keySet()) {
			fIsInstanceOf = conditions.get(condition).isInstanceOf(
					rule.getConditionValue(condition));
			if (!fIsInstanceOf) {
				break;
			}
		}

		return fIsInstanceOf;
	}

	public IRule setConditionValues(
			final Map<ICondition, IConditionValue> values) {
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
				stringBuffer
						.append(condition.getShortDescription())
						.append(':')
						.append(StringUtils.center(conditions.get(condition)
								.toString(), 3));
				fFirst = false;
			} else {
				stringBuffer
						.append(',')
						.append(condition.getShortDescription())
						.append(':')
						.append(StringUtils.center(conditions.get(condition)
								.toString(), 3));
			}
		}

		fFirst = true;

		stringBuffer.append("-->");

		for (final IAction action : getActions().keySet()) {
			if (fFirst) {
				stringBuffer
						.append(action.getShortDescription())
						.append(':')
						.append(StringUtils.center(actions.get(action)
								.toString(), 4));
			} else {
				stringBuffer
						.append(',')
						.append(action.getShortDescription())
						.append(':')
						.append(StringUtils.center(actions.get(action)
								.toString(), 4));
			}
		}

		return stringBuffer.toString();
	}

	@Override
	public boolean equals(final Object obj) {
		// TODO Auto-generated method stub
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
					result = getConditionValue(condition).compareTo(
							rule.getConditionValue(condition));
					if (result != 0) {
						break;
					}
				}
			}

			if (result == 0) {
				for (final IAction action : actions.keySet()) {
					result = getActionValue(action).compareTo(
							rule.getActionValue(action));
					if (result != 0) {
						break;
					}
				}
			}
		}

		return result;
	}

	public Map<IAction, IValue> getActions() {
		return actions;
	}

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

	public void remove(final IAction action) {
		actions.remove(action);
		fire();
	}

	public void remove(final ICondition condition) {
		conditions.remove(condition);
		fire();
	}

	@Override
	public IRule setActionValue(String action, IValue value) {
		return setActionValue(new BinaryAction(action), value);
	}

	@Override
	public void addPropertyChangeListener(final EventBus eventBus) {
		assert this.eventBus == null || eventBus == this.eventBus;
		this.eventBus = eventBus;
	}

}
