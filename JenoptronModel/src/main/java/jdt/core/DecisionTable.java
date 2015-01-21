package jdt.core;

import java.beans.PropertyChangeEvent;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;

import jdt.core.binary.BinaryAction;
import jdt.core.binary.BinaryActionValue;
import jdt.core.binary.BinaryCondition;
import jdt.core.binary.BinaryConditionValue;
import jdt.core.category.IGroup;
import jdt.core.events.PropChangeEvent;
import jdt.icore.IAction;
import jdt.icore.ICondition;
import jdt.icore.IConditionValue;
import jdt.icore.IDecisionTable;
import jdt.icore.IRule;
import jdt.icore.IValue;
import jdt.util.DTException;
import jdt.util.collections.IterableWrapper;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.log4j.Logger;

public class DecisionTable extends JDTModel implements IDecisionTable {
	/**
	 *
	 */
	private static final Logger logger = Logger.getLogger(IDecisionTable.class);
	private static final long serialVersionUID = -8708581753233948737L;
	private final List<IRule> rules;
	private final List<IAction> actions;
	private final List<ICondition> tableConditions;
	private final List<IGroup> groups;
	private String shortDescription;
	// @deprecated
	private File file;

	private final transient Predicate predicateLegalRule = new LegalRulePredicate(
			this);

	private final transient RuleComparator ruleComparator = new RuleComparator(
			this);

	public DecisionTable() {
		this("Some Decision Table");
	}

	public DecisionTable(final String shortDescription) {
		this(shortDescription, BinaryActionValue.UNKNOWN);
	}

	public DecisionTable(final IValue defaultActionValue) {
		this("Some Decision Table", defaultActionValue);
	}

	public DecisionTable(final String shortDescription,
			final IValue defaultActionValue) {
		super();
		rules = new ArrayList<IRule>();
		actions = new ArrayList<IAction>();
		tableConditions = new ArrayList<ICondition>();
		groups = new ArrayList<IGroup>();
		this.shortDescription = shortDescription;
		this.defaultActionValue = defaultActionValue;
	}

	/**
	 * writeObject.
	 * 
	 * Implemented because FindBugs complains otherwise.
	 * 
	 * @param out
	 * @throws IOException
	 */
	private void writeObject(final ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
	}

	/**
	 * readObject.
	 * 
	 * Implemented because FindBugs complains otherwise.
	 * 
	 * @param in
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void readObject(final ObjectInputStream in) throws IOException,
			ClassNotFoundException {
		in.defaultReadObject();
	}

	@Override
	public boolean equals(final Object obj) {
		boolean fEquals = false;

		if (obj instanceof DecisionTable) {
			final IDecisionTable decisionTable = (IDecisionTable) obj;
			fEquals = tableConditions.equals(decisionTable.getConditions())
					&& actions.equals(decisionTable.getActions()) &&

					rules.equals(decisionTable.getRules());
		}

		logger.debug("END equals(Object obj)");
		return fEquals;
	}

	public IDecisionTable deepcopy() {
		final IDecisionTable table = new DecisionTable();

		for (final ICondition condition : tableConditions) {
			table.add(condition.deepcopy());
		}

		for (final IAction action : actions) {
			table.add(action.deepcopy());
		}

		table.split();

		table.setRules(rules);

		logger.debug("END deepcopy()");
		return table;
	}

	public IDecisionTable add(final IAction action) {
		doAdd(action);
		fire();
		logger.debug("END add(IAction action)");

		return this;
	}

	public IDecisionTable add(final IAction... newActions) {
		for (final IAction action : newActions) {
			doAdd(action);
		}

		fire();
		logger.debug("END add(IAction... action)");

		return this;
	}

	public IDecisionTable addActions(
			final Collection<? extends IAction> newActions) {
		for (final IAction action : newActions) {
			doAdd(action);
		}

		fire();
		logger.debug("END add(Collection<? extends IAction>)");

		return this;
	}

	private void doAdd(final IAction action) {
		actions.add(action);

		addToAllRules(action);

		action.addPropertyChangeListener(eventBus);
	}

	@Override
	public IDecisionTable add(final Collection<? extends ICondition> conditions) {
		for (final ICondition condition : conditions) {
			doAdd(condition);
		}
		fire();

		return this;
	}

	public IDecisionTable add(final ICondition condition) throws DTException {
		doAdd(condition);
		fire();

		logger.debug("END addCondition(final ICondition condition)");
		return this;
	}

	public IDecisionTable add(final ICondition... conditions)
			throws DTException {
		for (final ICondition condition : conditions) {
			doAdd(condition);
		}
		fire();

		logger.debug("END addCondition(final ICondition condition)");
		return this;
	}

	private void doAdd(final ICondition condition) {
		// 0. Check presence of condition.
		if (tableConditions.contains(condition)) {
			throw new DTException("You cannot add a condition twice");
		}

		// 1. Add new condition to our list.
		tableConditions.add(condition);

		// 2. For each rule, add condition with default value.
		if (rules.size() == 0) {
			split();
		} else {
			addToAllRules(condition);
		}

		condition.addPropertyChangeListener(eventBus);
	}

	public IDecisionTable addActions(final List<IAction> additionalActions) {
		for (final IAction action : additionalActions) {
			add(action);
		}

		logger.debug("END addActions( List<IAction> actions)");
		return this;
	}

	public IDecisionTable addConditions(
			final List<ICondition> additionalConditions) {
		for (final ICondition condition : additionalConditions) {
			add(condition);
		}

		logger.debug("END addConditions( List<ICondition> conditions)");
		return this;
	}

	public IDecisionTable split() {
		suppressFire();

		final int size = tableConditions.size();

		if (size == 0) {
			rules.clear();
		} else {
			final List<IRule> rules2 = new ArrayList<IRule>();

			final Stack<ICondition> conditionStack = new Stack<ICondition>();

			for (int i = tableConditions.size() - 1; i >= 0; i--) {
				conditionStack.push(tableConditions.get(i));
			}

			// Create all rules possible.
			createRules(rules2, conditionStack,
					new HashMap<ICondition, IConditionValue>());

			// These rules only have conditions yet. Give m actions.
			for (final IRule rule : rules2) {
				for (final IAction action : actions) {
					rule.addAction(action, defaultActionValue);
				}
			}

			// Now copy action values from the old rules.
			for (final IRule rule : rules2) {
				final IRule existingRule = findRule(rule);

				if (existingRule == null) {
					for (final IAction action : actions) {
						rule.setActionValue(action, action.getUnknownValue());
					}
				} else {
					for (final IAction action : actions) {
						rule.setActionValue(action,
								existingRule.getActionValue(action));
					}
				}
			}
			Collections.sort(rules2, ruleComparator);
			rules.clear();
			rules.addAll(rules2);
		}

		resumeFire();

		logger.debug("END split()");
		return this;
	}

	/**
	 * Find a rule with the same conditions as the rule provided.
	 * 
	 * Especially, if a condition of a rule has the value IRRELEVANT, while
	 * we're looking for the value TRUE, then this condition is equivalent.
	 * 
	 * @param rule
	 * @return
	 */
	private IRule findRule(final IRule rule) {
		IRule equivalentRule = null;

		for (final IRule existingRule : rules) {
			if (rule.isInstanceOf(existingRule)) {
				equivalentRule = existingRule;
				break;
			}
		}

		logger.debug("END findRule( final IRule rule )");
		return equivalentRule;
	}

	private void createRules(final List<IRule> rules2,
			final Stack<ICondition> stack,
			final Map<ICondition, IConditionValue> listOrderedMap) {
		if (stack.isEmpty()) {
			// No more conditions to process.
			// Create a rule.
			// Set all its condition values to the values in the queue.
			final IRule rule = new Rule();
			rule.addPropertyChangeListener(eventBus);
			rule.setConditionValues(listOrderedMap);
			rules2.add(rule);
		} else {
			// More conditions to process.
			// Pop the condition.
			// For each possible value:
			// push it into the map.
			// call createRules.
			final ICondition condition = stack.pop();

			for (final IConditionValue value : condition.getPossibleValues()) {
				listOrderedMap.put(condition, value);
				createRules(rules2, stack, listOrderedMap);
				listOrderedMap.remove(condition);
			}

			stack.push(condition);
		}
		logger.debug("END createRules(final List<IRule> rules2, final Queue<ICondition> queue, final Map<ICondition,IValue> values)");
	}

	@SuppressWarnings("unchecked")
	public List<IRule> getRules() {
		final List<IRule> applicableRules = (List<IRule>) CollectionUtils
				.select(rules, predicateLegalRule);

		logger.debug("END List<IRule> getRules(): " + applicableRules.size());
		return applicableRules;
	}

	public List<IRule> getAllRules() {
		logger.debug("END List<IRule> getRules(): " + rules.size());
		return rules;
	}

	public Iterable<IRule> getLegalRulesIterable() {
		logger.debug("END List<IRule> getLegalRules(): " + rules.size());

		return new IterableWrapper<IRule>(rules, predicateLegalRule);
	}

	@Override
	public String toString() {
		final StringBuffer stringBuffer = new StringBuffer();

		stringBuffer.append(simpleDump("\"", "\\n\" +\n"));

		stringBuffer.append(getShortDescription()).append("\nRules:\n");

		for (final IRule rule : rules) {
			stringBuffer.append(rule.toString()).append('\n');
		}

		stringBuffer.append("\nConditions:\n");

		for (final ICondition condition : tableConditions) {
			stringBuffer.append(condition.toString()).append('\n');
		}

		stringBuffer.append("\nActions:\n");

		for (final IAction action : actions) {
			stringBuffer.append(action.toString()).append('\n');
		}

		return stringBuffer.toString();
	}

	/**
	 * Create a rule with default values.
	 * 
	 * Create a rule with default values. Add it to the existing rule set.
	 * Return it.
	 */
	public IRule createRule() {
		final Rule rule = new Rule();
		rule.addPropertyChangeListener(eventBus);
		rules.add(rule);
		logger.debug("END createRule()");
		return rule;
	}

	public List<ICondition> getConditions() {
		logger.debug("END getConditions(): " + tableConditions.size());
		return tableConditions;
	}

	public List<IAction> getActions() {
		logger.debug("END getActions()");
		return actions;
	}

	public boolean isValid(final IRule rule) {
		boolean fValid = true;

		for (final IGroup group : groups) {
			if (!group.isValid(rule)) {
				fValid = false;
				break;
			}
		}

		return fValid;
	}

	public boolean isInvalid(final IRule rule) {
		boolean fInvalid = false;

		for (final IGroup group : groups) {
			if (!group.isValid(rule)) {
				fInvalid = true;
				break;
			}
		}

		return fInvalid;
	}

	public IDecisionTable reduce() {
		suppressFire();

		// 1. Split.
		split();

		int step = 1;

		final int size = rules.size();

		final boolean[] invalidRules = new boolean[size];
		{
			int i = 0;
			for (final IRule rule : rules) {
				invalidRules[i] = isInvalid(rule);
				i++;
			}
		}

		final List<ICondition> handledConditions = new ArrayList<ICondition>();
		final boolean[] reduced = new boolean[size];

		final List<IRule> _rules = new ArrayList<IRule>(rules);

		logReducedDecisionTable(reduced, _rules);

		for (int i = tableConditions.size() - 1; i >= 0; i--) {
			final ICondition condition = tableConditions.get(i);

			reduce(condition, handledConditions, step, reduced, invalidRules,
					_rules);

			handledConditions.add(condition);
			step = step * 2;

			logReducedDecisionTable(reduced, _rules);
		}

		final SortedSet<IRule> reducedRules = new TreeSet<IRule>();

		int i = 0;
		for (final IRule rule : _rules) {
			if (!reduced[i] && !invalidRules[i]) {
				reducedRules.add(rule);
			}
			i++;
		}

		rules.clear();
		rules.addAll(reducedRules);

		Collections.sort(rules, ruleComparator);

		resumeFire();

		logger.debug("END reduce()");
		return this;
	}

	private void logReducedDecisionTable(final boolean[] reduced,
			final List<IRule> _rules) {
		if (logger.isDebugEnabled()) {
			final StringBuffer stringBuffer = simpleDump(reduced, _rules);

			logger.debug(stringBuffer);
		}
	}

	public StringBuffer simpleDump() {
		return simpleDump(null, getRules());
	}

	public StringBuffer simpleDump(final String start, final String end) {
		return simpleDump(null, getRules(), start, end);
	}

	private StringBuffer simpleDump(final boolean[] reduced,
			final List<IRule> irules) {
		return simpleDump(reduced, irules, "", "\n");
	}

	private StringBuffer simpleDump(final boolean[] reduced,
			final List<IRule> irules, final String start, final String end) {
		final StringBuffer stringBuffer = new StringBuffer();
		final int size = irules.size();
		for (final ICondition condition : tableConditions) {
			stringBuffer.append(start);
			for (int i = 0; i < size; i++) {
				if (reduced != null && reduced[i]) {
					stringBuffer.append("*");
				} else {
					final IRule rule = irules.get(i);
					final IConditionValue conditionValue = rule
							.getConditionValue(condition);
					final char value;
					if (conditionValue == null) {
						value = '0';
					} else if (conditionValue.equals(BinaryConditionValue.YES)) {
						value = 'Y';
					} else if (conditionValue.equals(BinaryConditionValue.NO)) {
						value = 'N';
					} else if (conditionValue
							.equals(BinaryConditionValue.INITIAL_VALUE)) {
						value = '-';
					} else if (conditionValue
							.equals(BinaryConditionValue.DEFAULT_VALUE)) {
						value = '?';
					} else {
						value = 'X';
					}

					stringBuffer.append(value);
				}
			}
			stringBuffer.append(' ').append(condition.getShortDescription())
					.append(end);
		}
		for (final IAction action : actions) {
			stringBuffer.append(start);
			for (int i = 0; i < size; i++) {
				final IRule rule = irules.get(i);
				final IValue value = rule.getActionValue(action);
				final char display;
				if (value == null) {
					display = '0';
				} else if (value.equals(BinaryActionValue.DO)) {
					display = 'X';
				} else if (value.equals(BinaryActionValue.DONT)) {
					display = ' ';
				} else if (value.equals(BinaryActionValue.INITIAL_VALUE)) {
					display = '?';
				} else if (value.equals(BinaryActionValue.DEFAULT_VALUE)) {
					display = 'D';
				} else {
					display = 'Q';
				}
				stringBuffer.append(display);
			}
			stringBuffer.append(' ').append(action.getShortDescription())
					.append(end);
		}
		return stringBuffer;
	}

	private void reduce(final ICondition condition,
			final List<ICondition> handledConditions, final int step,
			final boolean[] reduced, final boolean[] invalidRules,
			List<IRule> _rules) {
		final int size = rules.size();
		final boolean[] processedRules = new boolean[size];

		for (int index = 0; index < size; index++) {
			final IRule rule = _rules.get(index);
			final int otherIndex = index + step;
			if (!processedRules[index] && !reduced[index]
					&& (otherIndex < size)) {
				final IRule otherRule = _rules.get(otherIndex);

				if (sameActions(rule, otherRule)
						&& (reduced[otherIndex] || sameStrictHandledConditions(
								rule, otherRule, handledConditions))) {
					if (invalidRules[index] && !invalidRules[otherIndex]) {
						otherRule.setConditionValue(condition,
								condition.getIrrelevantValue());

						// Swap rules
						_rules.set(index, otherRule);
						_rules.set(otherIndex, rule);

						reduced[otherIndex] = true;

						invalidRules[index] = false;
						invalidRules[otherIndex] = true;
					} else if (invalidRules[otherIndex] && invalidRules[index]) {
						// Both rules are invalid
						rule.setConditionValue(condition,
								condition.getIrrelevantValue());
						reduced[otherIndex] = true;
					} else {
						rule.setConditionValue(condition,
								condition.getIrrelevantValue());
						reduced[otherIndex] = true;
					}
				}

				processedRules[index] = true;
				processedRules[otherIndex] = true;
			}
		}
		logger.debug("END reduce( final ICondition condition, final List<ICondition> handledConditions, final int step, final boolean[] reduced)");
	}

	private boolean sameStrictHandledConditions(final IRule rule,
			final IRule originalRule, final List<ICondition> handledConditions) {
		boolean fSame = true;

		for (final ICondition condition : handledConditions) {
			final IConditionValue conditionValue = rule
					.getConditionValue(condition);
			final IConditionValue originalConditionValue = originalRule
					.getConditionValue(condition);
			if (!conditionValue.equals(originalConditionValue)) {
				fSame = false;
				break;
			}
		}

		return fSame;
	}

	private boolean sameHandledConditions(final IRule nonReducedRule,
			final IRule possibleReducedRule,
			final List<ICondition> handledConditions) {
		boolean fSame = true;

		for (final ICondition condition : handledConditions) {
			if (!(nonReducedRule.getConditionValue(condition).equals(
					possibleReducedRule.getConditionValue(condition)) || possibleReducedRule
					.getConditionValue(condition).equals(
							possibleReducedRule.getConditionValue(condition)
									.getInitialValue()))) {
				fSame = false;
				break;
			}
		}

		return fSame;
	}

	private boolean sameActions(final IRule rule1, final IRule rule2) {
		boolean fEquals = true;

		for (final IAction action : actions) {
			if (!rule1.getActionValue(action).equals(
					rule2.getActionValue(action))) {
				fEquals = false;
				break;
			}
		}

		return fEquals;
	}

	public int nrConditions() {
		return tableConditions.size();
	}

	public int nrActions() {
		return actions.size();
	}

	public int nrRules() {
		return rules.size();
	}

	public boolean showsAllRules() {
		return Math.pow(2.0, tableConditions.size()) == rules.size();
	}

	public IDecisionTable insert(final int position, final IAction action) {
		actions.add(position, action);
		addToAllRules(action);

		action.addPropertyChangeListener(eventBus);
		fire();

		logger.debug("END insert(int position, IAction action)");
		return this;
	}

	public IDecisionTable insert(final int position, final ICondition condition) {
		tableConditions.add(position, condition);
		addToAllRules(condition);

		condition.addPropertyChangeListener(eventBus);
		fire();

		logger.debug("END insert(int position, ICondition condition)");
		return this;
	}

	private void addToAllRules(final ICondition condition) {
		for (final IRule rule : rules) {
			rule.addCondition(condition);
		}
		logger.debug("END addToAllRules(ICondition condition)");
	}

	private void addToAllRules(final IAction action) {
		for (final IRule rule : rules) {
			rule.addAction(action, defaultActionValue);
		}
		logger.debug("END addToAllRules(IAction action)");
	}

	public IRule getRule(final int ruleNr) {
		return new ArrayList<IRule>(rules).get(ruleNr);
	}

	public void setRules(final List<IRule> possibleReducedRules) {
		suppressFire();

		split();
		for (final IRule possibleReducedRule : possibleReducedRules) {
			for (final IRule nonReducedRule : rules) {
				if (sameHandledConditions(nonReducedRule, possibleReducedRule,
						getConditions())) {
					for (final IAction action : actions) {
						nonReducedRule.setActionValue(action,
								possibleReducedRule.getActionValue(action));
					}
				}
			}
		}

		resumeFire();
		logger.debug("END setRules( List<IRule> rules)");
	}

	public IDecisionTable down(final IAction action) {
		final int index = actions.indexOf(action);

		if ((index > -1) && (index < actions.size() - 1)) {
			// otherwise, the action does not exist,
			// or the action is the last one.
			final IAction otherAction = actions.get(index + 1);
			actions.set(index, otherAction);
			actions.set(index + 1, action);
		}

		fire();
		return this;
	}

	public IDecisionTable up(final IAction action) {
		final int index = actions.indexOf(action);

		if ((index > 0) && (index < actions.size())) {
			// otherwise, the action does not exist,
			// or the action is the first one.
			final IAction otherAction = actions.get(index - 1);
			actions.set(index, otherAction);
			actions.set(index - 1, action);
		}

		fire();
		return this;
	}

	public IDecisionTable down(final ICondition condition) {
		final int index = tableConditions.indexOf(condition);

		if ((index > -1) && (index < tableConditions.size() - 1)) {
			// otherwise, the condition does not exist,
			// or the condition is the last one.
			final ICondition otherCondition = tableConditions.get(index + 1);
			tableConditions.set(index, otherCondition);
			tableConditions.set(index + 1, condition);
		}

		sortRules();

		fire();
		return this;
	}

	public IDecisionTable up(final ICondition condition) {
		final int index = tableConditions.indexOf(condition);

		if ((index > 0) && (index < tableConditions.size())) {
			// otherwise, the condition does not exist,
			// or the condition is the first one.
			final ICondition otherCondition = tableConditions.get(index - 1);
			tableConditions.set(index, otherCondition);
			tableConditions.set(index - 1, condition);
		}

		sortRules();

		fire();
		return this;
	}

	private void sortRules() {
		final List<IRule> rules = getRules();

		Collections.sort(rules);
		setRules(rules);
	}

	// @Override
	public IDecisionTable getSubtable(final String... conditions) {
		final IDecisionTable subtable = new DecisionTable();

		for (final String condition : conditions) {
			for (final ICondition condition2 : getConditions()) {
				if (condition.equals(condition2.getShortDescription())) {
					subtable.add(condition2);
				}
			}
		}

		for (final IAction action : actions) {
			subtable.add(action);
		}

		addActionValuesToSubtable(subtable);

		return subtable;
	}

	// @Override
	public IDecisionTable getSubtable(final int[] requestedConditions,
			final int[] requestedActions) {
		final IDecisionTable subtable = new DecisionTable();

		{
			for (final int element : requestedConditions) {
				subtable.add(getConditions().get(element));
			}
		}

		{
			for (final int element : requestedActions) {
				subtable.add(getActions().get(element));
			}
		}

		addActionValuesToSubtable(subtable);

		return subtable;
	}

	private void addActionValuesToSubtable(final IDecisionTable subtable) {
		subtable.split();

		{
			for (final IRule subtableRule : subtable.getRules()) {
				final Map<ICondition, IConditionValue> conditions = subtableRule
						.getConditions();

				final List<IRule> interestingRules = getRules(conditions);

				for (final IAction action : subtableRule.getActions().keySet()) {
					IValue value = null;
					for (final IRule rule : interestingRules) {
						final IValue value2 = rule.getActionValue(action);

						if (value == null) {
							value = value2;
						} else if (!value.equals(value2)) {
							value = BinaryActionValue.initialValue();
							break;
						}
					}

					if (value == null) {
						value = BinaryActionValue.initialValue();
					}

					subtableRule.setActionValue(action, value);
				}
			}
		}
	}

	public List<IRule> getRules(
			final Map<ICondition, IConditionValue> certainConditions) {
		final List<IRule> certainConditionRules = new ArrayList<IRule>();

		for (final IRule rule : rules) {
			boolean sameConditions = true;
			for (final Map.Entry<ICondition, IConditionValue> conditionEntry : certainConditions
					.entrySet()) {
				if (!conditionEntry.getValue().equals(
						rule.getConditionValue(conditionEntry.getKey()))) {
					sameConditions = false;
					break;
				}
			}

			if (sameConditions) {
				certainConditionRules.add(rule);
			}
		}

		return certainConditionRules;
	}

	public void remove(final IAction action) {
		for (final IRule rule : rules) {
			rule.remove(action);
		}

		actions.remove(action);

		// action.removePropertyChangeListener(this);
		fire();
	}

	public void remove(final ICondition condition) {
		final List<IRule> newRules = new ArrayList<IRule>();

		while (!rules.isEmpty()) {
			final IRule rule1 = rules.remove(0);
			rule1.remove(condition);
			final IRule rule2 = findRule(rule1);

			if (rule2 != null) {
				rules.remove(rule2);
				for (final IAction action : actions) {
					if (!rule1.getActionValue(action).equals(
							rule2.getActionValue(action))) {
						// Different values so make them unknown.
						final IValue value = rule1.getActionValue(action)
								.getInitialValue();
						rule1.setActionValue(action, value);
					}
				}

				if (!rule1.getConditions().isEmpty()) {
					newRules.add(rule1);
				}
			}
		}

		rules.clear();
		rules.addAll(newRules);

		tableConditions.remove(condition);

		// condition.removePropertyChangeListener(this);
		fire();
	}

	public void propertyChange(final PropertyChangeEvent propertyChangeEvent) {
		logger.debug("propertyChange on " + getClass().getName() + ":"
				+ propertyChangeEvent.getPropertyName());
		fire();
	}

	// Method is used for saving the GUI. Keep it public !
	public void setActions(final List<IAction> actions) {
		this.actions.addAll(actions);
		logger.debug("END setActions(List<IAction> actions)");
	}

	// Method is used for saving the GUI. Keep it public !
	public void setConditions(final List<ICondition> conditions) {
		this.tableConditions.addAll(conditions);
		logger.debug("END setConditions(List<ICondition> conditions)");
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(final String shortDescription) {
		final String oldShortDescription = this.shortDescription;
		this.shortDescription = shortDescription;
		// firePropertyChange(PROP_SHORT_DESCRIPTION, oldShortDescription,
		// shortDescription);
		fire(new PropChangeEvent(oldShortDescription, shortDescription));
	}

	private void fire(PropChangeEvent propChangeEvent) {
		// TODO Auto-generated method stub

	}

	public IDecisionTable add(final IGroup group) {
		{
			for (final ICondition condition : group.conditions()) {
				if (!tableConditions.contains(condition)) {
					add(condition);
				}
			}
		}
		{
			for (final IAction action : group.actions()) {
				if (!actions.contains(action)) {
					add(action);
				}
			}
		}
		groups.add(group);

		split();

		for (final IRule rule : rules) {
			group.setActionValues(rule);
		}

		return this;
	}

	public List<IGroup> getGroups() {
		return groups;
	}

	public void setGroups(final List<IGroup> groups) {
		groups.addAll(groups);
		logger.debug("END setConditionGroups(List<IGroup> groups)");
	}

	public File getFile() {
		return file;
	}

	public void setFile(final File file) {
		this.file = file;
	}

	public boolean hasGroups() {
		return groups.size() > 0;
	}

	@Override
	public IDecisionTable addBinaryAction(String action) {
		return add(new BinaryAction(action));
	}

	@Override
	public IDecisionTable addBinaryActions(String... actions) {
		for (final String action : actions) {
			addBinaryAction(action);
		}
		return this;
	}

	@Override
	public IDecisionTable addBinaryCondition(String condition) {
		return add(new BinaryCondition(condition));
	}

	@Override
	public IDecisionTable addBinaryConditions(String... conditions) {
		for (final String condition : conditions) {
			addBinaryCondition(condition);
		}
		return this;
	}

	@Override
	public IDecisionTable otherwise() {
		suppressFire();

		reduce();

		resumeFire();

		return this;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	public IRule getRule(final IConditionValue... conditions) {
		if (conditions.length != this.tableConditions.size()) {
			throw new DTException("Incorrect number of conditions");
		}

		for (final IRule rule : rules) {
			if (sameConditions(rule, conditions)) {
				if (isInvalid(rule)) {
					return null;
				} else {
					return rule;
				}
			}
		}

		return null;
	}

	@Override
	public IDecisionTable setActionValues(final IRule rule,
			final IValue... actions) {
		if (actions.length != this.actions.size()) {
			throw new DTException("Incorrect number of actions");
		}

		for (int index = 0; index < actions.length; index++) {
			rule.setActionValue(getActions().get(index), actions[index]);
		}

		return this;
	}

	@Override
	public IDecisionTable setActionValues(final IRule rule, final IValue value) {
		for (final IAction action : actions) {
			rule.setActionValue(action, value);
		}

		return this;
	}

	@Override
	public IDecisionTable setActionValues(final IValue value) {
		for (final IRule rule : rules) {
			setActionValues(rule, value);
		}
		return this;
	}

	@Override
	public IDecisionTable setActionValues(final IValue... values) {
		for (final IRule rule : rules) {
			setActionValues(rule, values);
		}
		return this;
	}

	@Override
	public IDecisionTable setActionValues(final IValue value,
			final IAction action) {
		for (final IRule rule : rules) {
			rule.setActionValue(action, value);
		}

		return this;
	}

	@Override
	public boolean sameConditions(final IRule rule,
			final IConditionValue... conditionValues) {
		if (conditionValues.length != this.tableConditions.size()) {
			throw new DTException("Incorrect number of conditions");
		}

		for (int index = 0; index < conditionValues.length; index++) {
			if (!rule.getConditionValue(getConditions().get(index)).equals(
					conditionValues[index])) {
				return false;
			}
		}

		return true;
	}

	private IValue defaultActionValue = BinaryActionValue.UNKNOWN;

	@Override
	public IDecisionTable setDefaultActionValue(
			final BinaryActionValue binaryActionValue) {
		this.defaultActionValue = binaryActionValue;

		return this;
	}

	@Override
	public void addPropertyChangeListener(final Object object) {
		eventBus.register(object);
	}
}