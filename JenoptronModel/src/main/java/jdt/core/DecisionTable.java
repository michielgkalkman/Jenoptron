package jdt.core;

import java.beans.PropertyChangeEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.Stack;
import java.util.TreeSet;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.log4j.Logger;

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

public class DecisionTable extends JDTModel implements IDecisionTable {
	private static final List<IGroup> EMPTY_GROUPS = Collections.unmodifiableList(new ArrayList<IGroup>());
	private static final List<ICondition> EMPTY_CONDITIONS = Collections.unmodifiableList(new ArrayList<ICondition>());
	private static final List<IAction> EMPTY_ACTIONS = Collections.unmodifiableList(new ArrayList<IAction>());
	private static final List<IRule> EMPTY_RULES = Collections.unmodifiableList(new ArrayList<IRule>());
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
	private IValue defaultActionValue = BinaryActionValue.UNKNOWN;

	private final transient Predicate predicateLegalRule = new LegalRulePredicate(this);

	public DecisionTable() {
		this("Some Decision Table");
	}

	public DecisionTable(final String shortDescription) {
		this(shortDescription, BinaryActionValue.UNKNOWN);
	}

	public DecisionTable(final IValue defaultActionValue) {
		this("Some Decision Table", defaultActionValue);
	}

	public DecisionTable(final String shortDescription, final IValue defaultActionValue) {
		this(EMPTY_RULES, EMPTY_ACTIONS, EMPTY_CONDITIONS, EMPTY_GROUPS, shortDescription, defaultActionValue);
	}

	private DecisionTable(final List<IRule> rules, final List<IAction> actions, final List<ICondition> tableConditions,
			final List<IGroup> groups, final String shortDescription, final IValue defaultActionValue) {
		super();
		this.rules = rules;
		this.actions = actions;
		this.tableConditions = tableConditions;
		this.groups = groups;
		this.shortDescription = shortDescription;
		this.defaultActionValue = defaultActionValue;
	}

	private IDecisionTable createDecisionTable(final List<ICondition> newTableConditions,
			final List<IAction> newActions, final List<IRule> newRules, final List<IGroup> newGroups) {
		// TODO Auto-generated method stub
		return new DecisionTable(newRules, newActions, newTableConditions, groups, shortDescription,
				defaultActionValue);
	}

	private DecisionTable createDecisionTable(final List<IRule> newRules) {
		return new DecisionTable(newRules, actions, tableConditions, groups, shortDescription, defaultActionValue);
	}

	private IDecisionTable createDecisionTable(final List<IRule> newRules, final List<IAction> newActions,
			final List<ICondition> newTableConditions) {
		return new DecisionTable(newRules, newActions, newTableConditions, groups, shortDescription,
				defaultActionValue);
	}

	private IDecisionTable createDecisionTable(final List<IRule> newRules, final List<IAction> newActions) {
		return new DecisionTable(newRules, newActions, tableConditions, groups, shortDescription, defaultActionValue);
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
	private void readObject(final ObjectInputStream in) throws IOException, ClassNotFoundException {
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

	@Override
	public IDecisionTable add(final IAction action) {
		final List<IAction> newActionList = new ArrayList<>(actions);

		newActionList.add(action);

		final List<IRule> newRules = addToAllRules(action);

		fire();
		logger.debug("END add(Collection<? extends IAction>)");

		return createDecisionTable(newRules, newActionList);
	}

	@Override
	public IDecisionTable add(final IAction... newActions) {
		final List<IAction> newActionList = new ArrayList<>();

		for (final IAction action : newActions) {
			if (!actions.contains(action)) {
				newActionList.add(action);
			}
		}

		final List<IRule> newRules = new ArrayList<>();

		for (final IRule rule : rules) {
			newRules.add(rule.addActions(newActionList, defaultActionValue));
		}

		newActionList.addAll(actions);

		fire();

		logger.debug("END add(Collection<? extends IAction>)");

		return createDecisionTable(newRules, newActionList);
	}

	@Override
	public IDecisionTable addActions(final Collection<? extends IAction> newActions) {
		final List<IAction> newActionList = new ArrayList<>(actions);

		for (final IAction action : newActions) {
			newActionList.add(action);
		}

		final List<IRule> newRules = addToAllRules(newActions);

		fire();
		logger.debug("END add(Collection<? extends IAction>)");

		return createDecisionTable(newRules, newActionList);
	}

	@Override
	public IDecisionTable add(final ICondition condition) throws DTException {
		final IDecisionTable newDecisionTable = doAdd(condition);
		fire();

		logger.debug("END addCondition(final ICondition condition)");
		return newDecisionTable;
	}

	@Override
	public IDecisionTable add(final Collection<? extends ICondition> conditions) {
		final List<ICondition> newConditionList = new ArrayList<>();

		for (final ICondition condition : conditions) {
			if (!tableConditions.contains(condition)) {
				newConditionList.add(condition);
			}
		}

		for (final ICondition condition : tableConditions) {
			newConditionList.add(condition);
		}

		final List<IRule> newRules = addToAllRules(newConditionList);

		fire();

		logger.debug("END addCondition(final ICondition condition)");
		return createDecisionTable(newRules, actions, newConditionList);
	}

	@Override
	public IDecisionTable add(final ICondition... conditions) throws DTException {
		final List<ICondition> newConditionList = new ArrayList<>();

		for (final ICondition condition : conditions) {
			if (!tableConditions.contains(condition)) {
				newConditionList.add(condition);
			}
		}

		for (final ICondition condition : tableConditions) {
			newConditionList.add(condition);
		}

		final List<IRule> newRules = addToAllRules(newConditionList);

		fire();

		logger.debug("END addCondition(final ICondition condition)");
		return createDecisionTable(newRules, actions, newConditionList);
	}

	private IDecisionTable doAdd(final ICondition condition) {
		// 0. Check presence of condition.
		if (tableConditions.contains(condition)) {
			throw new DTException("You cannot add a condition twice");
		}

		final List<ICondition> newTableConditions = new ArrayList<>(tableConditions);
		// 1. Add new condition to our list.
		newTableConditions.add(condition);

		final List<IRule> newRules;

		// 2. For each rule, add condition with default value.
		if (rules.size() == 0) {
			newRules = splitRules(newTableConditions);
		} else {
			newRules = addToAllRules(condition);
		}

		return createDecisionTable(newRules, actions, newTableConditions);
	}

	// public IDecisionTable addActions(final List<IAction> additionalActions) {
	//
	// return cre
	// for (final IAction action : additionalActions) {
	// add(action);
	// }
	//
	// logger.debug("END addActions( List<IAction> actions)");
	// return this;
	// }

	public IDecisionTable addConditions(final List<ICondition> additionalConditions) {
		for (final ICondition condition : additionalConditions) {
			add(condition);
		}

		logger.debug("END addConditions( List<ICondition> conditions)");
		return this;
	}

	@Override
	public IDecisionTable split() {
		return createDecisionTable(splitRules());
	}

	private List<IRule> splitRules() {
		final List<ICondition> tmpConditions = tableConditions;

		final List<IRule> newRules = splitRules(tmpConditions);

		logger.debug("END split()");
		return newRules;
	}

	private List<IRule> splitRules(final List<ICondition> tmpConditions) {
		suppressFire();

		final List<IRule> newRules = new ArrayList<IRule>();

		final int size = tmpConditions.size();

		if (size > 0) {
			final Stack<ICondition> conditionStack = new Stack<ICondition>();

			for (int i = size - 1; i >= 0; i--) {
				conditionStack.push(tmpConditions.get(i));
			}

			// Create all rules possible.
			createRules(newRules, conditionStack, new HashMap<ICondition, IConditionValue>());

			// These rules only have conditions yet. Give m actions.
			if (!actions.isEmpty()) {
				for (final IRule rule : newRules) {
					for (final IAction action : actions) {
						rule.addAction(action, defaultActionValue);
					}
				}

				// Now copy action values from the old rules.
				for (final IRule rule : newRules) {
					final IRule existingRule = findRule(rule);

					if (existingRule == null) {
						for (final IAction action : actions) {
							rule.setActionValue(action, action.getUnknownValue());
						}
					} else {
						for (final IAction action : actions) {
							rule.setActionValue(action, existingRule.getActionValue(action));
						}
					}
				}
			}
			Collections.sort(newRules, new RuleComparator(tableConditions));
		}

		resumeFire();
		return newRules;
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

	private void createRules(final List<IRule> newRules, final Stack<ICondition> stack,
			final Map<ICondition, IConditionValue> listOrderedMap) {
		if (stack.isEmpty()) {
			// No more conditions to process.
			// Create a rule.
			// Set all its condition values to the values in the queue.
			final IRule rule = new Rule();
			rule.setConditionValues(listOrderedMap);
			newRules.add(rule);
		} else {
			// More conditions to process.
			// Pop the condition.
			// For each possible value:
			// push it into the map.
			// call createRules.
			final ICondition condition = stack.pop();

			for (final IConditionValue value : condition.getPossibleValues()) {
				listOrderedMap.put(condition, value);
				createRules(newRules, stack, listOrderedMap);
				listOrderedMap.remove(condition);
			}

			stack.push(condition);
		}
		logger.debug(
				"END createRules(final List<IRule> newRules, final Queue<ICondition> queue, final Map<ICondition,IValue> values)");
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<IRule> getRules() {
		final List<IRule> applicableRules = (List<IRule>) CollectionUtils.select(rules, predicateLegalRule);

		logger.debug("END List<IRule> getRules(): " + applicableRules.size());
		return applicableRules;
	}

	@Override
	public List<IRule> getAllRules() {
		logger.debug("END List<IRule> getRules(): " + rules.size());
		return rules;
	}

	@Override
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
		rules.add(rule);
		logger.debug("END createRule()");
		return rule;
	}

	@Override
	public List<ICondition> getConditions() {
		logger.debug("END getConditions(): " + tableConditions.size());
		return tableConditions;
	}

	@Override
	public List<IAction> getActions() {
		logger.debug("END getActions()");
		return actions;
	}

	@Override
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

	@Override
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

	@Override
	public IDecisionTable reduce() {
		suppressFire();

		final List<IRule> newRules = new ArrayList<>();

		// 1. Split.
		final List<IRule> splitRules = splitRules();

		int step = 1;

		final int size = splitRules.size();

		final boolean[] invalidRules = new boolean[size];
		{
			int i = 0;
			for (final IRule rule : splitRules) {
				invalidRules[i] = isInvalid(rule);
				i++;
			}
		}

		final List<ICondition> handledConditions = new ArrayList<ICondition>();
		final boolean[] reduced = new boolean[size];

		final List<IRule> _rules = new ArrayList<IRule>(splitRules);

		logReducedDecisionTable(reduced, _rules);

		for (int i = tableConditions.size() - 1; i >= 0; i--) {
			final ICondition condition = tableConditions.get(i);

			reduce(condition, handledConditions, step, reduced, invalidRules, _rules);

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

		newRules.addAll(reducedRules);

		Collections.sort(newRules, new RuleComparator(tableConditions));

		resumeFire();

		logger.debug("END reduce()");
		return createDecisionTable(newRules);
	}

	private void logReducedDecisionTable(final boolean[] reduced, final List<IRule> _rules) {
		if (logger.isDebugEnabled()) {
			final StringBuffer stringBuffer = simpleDump(reduced, _rules);

			logger.debug(stringBuffer);
		}
	}

	@Override
	public StringBuffer simpleDump() {
		return simpleDump(null, getRules());
	}

	public StringBuffer simpleDump(final String start, final String end) {
		return simpleDump(null, getRules(), start, end);
	}

	private StringBuffer simpleDump(final boolean[] reduced, final List<IRule> irules) {
		return simpleDump(reduced, irules, "", "\n");
	}

	private StringBuffer simpleDump(final boolean[] reduced, final List<IRule> irules, final String start,
			final String end) {
		final StringBuffer stringBuffer = new StringBuffer();
		final int size = irules.size();
		for (final ICondition condition : tableConditions) {
			stringBuffer.append(start);
			for (int i = 0; i < size; i++) {
				if (reduced != null && reduced[i]) {
					stringBuffer.append("*");
				} else {
					final IRule rule = irules.get(i);
					final IConditionValue conditionValue = rule.getConditionValue(condition);
					final char value;
					if (conditionValue == null) {
						value = '0';
					} else if (conditionValue.equals(BinaryConditionValue.YES)) {
						value = 'Y';
					} else if (conditionValue.equals(BinaryConditionValue.NO)) {
						value = 'N';
					} else if (conditionValue.equals(BinaryConditionValue.INITIAL_VALUE)) {
						value = '-';
					} else if (conditionValue.equals(BinaryConditionValue.DEFAULT_VALUE)) {
						value = '?';
					} else {
						value = 'X';
					}

					stringBuffer.append(value);
				}
			}
			stringBuffer.append(' ').append(condition.getShortDescription()).append(end);
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
			stringBuffer.append(' ').append(action.getShortDescription()).append(end);
		}
		return stringBuffer;
	}

	private void reduce(final ICondition condition, final List<ICondition> handledConditions, final int step,
			final boolean[] reduced, final boolean[] invalidRules, final List<IRule> _rules) {
		final int size = rules.size();
		final boolean[] processedRules = new boolean[size];

		for (int index = 0; index < size; index++) {
			final IRule rule = _rules.get(index);
			final int otherIndex = index + step;
			if (!processedRules[index] && !reduced[index] && (otherIndex < size)) {
				final IRule otherRule = _rules.get(otherIndex);

				if (sameActions(rule, otherRule)
						&& (reduced[otherIndex] || sameStrictHandledConditions(rule, otherRule, handledConditions))) {
					if (invalidRules[index] && !invalidRules[otherIndex]) {
						final IRule newOtherRule = otherRule.setConditionValue(condition,
								condition.getIrrelevantValue());

						// Swap rules
						_rules.set(index, newOtherRule);
						_rules.set(otherIndex, rule);

						reduced[otherIndex] = true;

						invalidRules[index] = false;
						invalidRules[otherIndex] = true;
					} else if (invalidRules[otherIndex] && invalidRules[index]) {
						// Both rules are invalid
						_rules.set(index, rule.setConditionValue(condition, condition.getIrrelevantValue()));
						reduced[otherIndex] = true;
					} else {
						_rules.set(index, rule.setConditionValue(condition, condition.getIrrelevantValue()));
						reduced[otherIndex] = true;
					}
				}

				processedRules[index] = true;
				processedRules[otherIndex] = true;
			}
		}
		logger.debug(
				"END reduce( final ICondition condition, final List<ICondition> handledConditions, final int step, final boolean[] reduced)");
	}

	private boolean sameStrictHandledConditions(final IRule rule, final IRule originalRule,
			final List<ICondition> handledConditions) {
		boolean fSame = true;

		for (final ICondition condition : handledConditions) {
			final IConditionValue conditionValue = rule.getConditionValue(condition);
			final IConditionValue originalConditionValue = originalRule.getConditionValue(condition);
			if (!conditionValue.equals(originalConditionValue)) {
				fSame = false;
				break;
			}
		}

		return fSame;
	}

	private boolean sameHandledConditions(final IRule nonReducedRule, final IRule possibleReducedRule,
			final List<ICondition> handledConditions) {
		boolean fSame = true;

		for (final ICondition condition : handledConditions) {
			if (!(nonReducedRule.getConditionValue(condition).equals(possibleReducedRule.getConditionValue(condition))
					|| possibleReducedRule.getConditionValue(condition)
							.equals(possibleReducedRule.getConditionValue(condition).getInitialValue()))) {
				fSame = false;
				break;
			}
		}

		return fSame;
	}

	private boolean sameActions(final IRule rule1, final IRule rule2) {
		boolean fEquals = true;

		for (final IAction action : actions) {
			if (!rule1.getActionValue(action).equals(rule2.getActionValue(action))) {
				fEquals = false;
				break;
			}
		}

		return fEquals;
	}

	@Override
	public int nrConditions() {
		return tableConditions.size();
	}

	@Override
	public int nrActions() {
		return actions.size();
	}

	@Override
	public int nrRules() {
		return rules.size();
	}

	@Override
	public boolean showsAllRules() {
		return Math.pow(2.0, tableConditions.size()) == rules.size();
	}

	@Override
	public IDecisionTable insert(final int position, final IAction action) {
		actions.add(position, action);
		addToAllRules(action);

		fire();

		logger.debug("END insert(int position, IAction action)");
		return this;
	}

	@Override
	public IDecisionTable insert(final int position, final ICondition condition) {
		final List<ICondition> newTableConditions = new ArrayList<>(tableConditions);

		newTableConditions.add(position, condition);
		final List<IRule> newRules = addToAllRules(condition);

		final IDecisionTable newDecisionTable = createDecisionTable(newRules, actions, newTableConditions);

		fire();

		logger.debug("END insert(int position, ICondition cond*ition)");
		return newDecisionTable;
	}

	private List<IRule> addToAllRules(final ICondition condition) {
		final List<IRule> newRules = new ArrayList<>();

		for (final IRule rule : rules) {
			newRules.add(rule.addCondition(condition));
		}

		logger.debug("END addToAllRules(ICondition condition)");
		return newRules;
	}

	private List<IRule> addToAllRules(final List<ICondition> conditions) {
		final List<IRule> newRules = new ArrayList<>();
		if (!conditions.isEmpty()) {
			final Stack<ICondition> conditionStack = new Stack<ICondition>();

			conditionStack.addAll(conditions);

			final List<Map<ICondition, IConditionValue>> possibleConditionValues = new ArrayList<>();

			getPossibleConditionValueCombinations(possibleConditionValues, conditionStack, new HashMap<>());

			if (rules.isEmpty()) {
				for (final Map<ICondition, IConditionValue> c2v : possibleConditionValues) {
					newRules.add(new Rule(c2v));
				}
			} else {
				for (final IRule iRule : rules) {
					for (final Map<ICondition, IConditionValue> c2v : possibleConditionValues) {
						newRules.add(iRule.add(c2v));
					}
				}
			}
		}
		logger.debug("END addToAllRules(ICondition condition)");
		return newRules;
	}

	private void getPossibleConditionValueCombinations(
			final List<Map<ICondition, IConditionValue>> possibleValueCombinations,
			final Stack<ICondition> conditionStack, final Map<ICondition, IConditionValue> c2v) {

		if (conditionStack.isEmpty()) {
			possibleValueCombinations.add(new HashMap<>(c2v));
		} else {
			final ICondition condition = conditionStack.pop();

			for (final IConditionValue value : condition.getPossibleValues()) {
				c2v.put(condition, value);
				getPossibleConditionValueCombinations(possibleValueCombinations, conditionStack, c2v);
				c2v.remove(condition);
			}

			conditionStack.push(condition);
		}
	}

	private List<IRule> addToAllRules(final IAction action) {
		final List<IRule> newRules = new ArrayList<>();
		for (final IRule rule : rules) {
			newRules.add(rule.addAction(action, defaultActionValue));
		}

		logger.debug("END addToAllRules(IAction action)");
		return newRules;
	}

	private List<IRule> addToAllRules(final Collection<? extends IAction> actions) {
		final List<IRule> newRules = new ArrayList<>();
		for (final IRule rule : rules) {
			newRules.add(rule.addActions(actions, defaultActionValue));
		}

		logger.debug("END addToAllRules(IAction action)");
		return newRules;
	}

	private List<IRule> addToAllRules(final IAction... actions) {
		final List<IRule> newRules;
		if (rules.isEmpty()) {
			// Create a single rule.
			newRules = new ArrayList<>();
			newRules.add(new Rule().addConditions(tableConditions).addActions(defaultActionValue, actions));
		} else {
			newRules = new ArrayList<>();
			for (final IRule rule : rules) {
				newRules.add(rule.addActions(defaultActionValue, actions));
			}
		}

		logger.debug("END addToAllRules(IAction action)");
		return newRules;
	}

	@Override
	public IRule getRule(final int ruleNr) {
		return new ArrayList<IRule>(rules).get(ruleNr);
	}

	@Override
	public void setRules(final List<IRule> possibleReducedRules) {
		suppressFire();

		split();
		for (final IRule possibleReducedRule : possibleReducedRules) {
			for (final IRule nonReducedRule : rules) {
				if (sameHandledConditions(nonReducedRule, possibleReducedRule, getConditions())) {
					for (final IAction action : actions) {
						nonReducedRule.setActionValue(action, possibleReducedRule.getActionValue(action));
					}
				}
			}
		}

		resumeFire();
		logger.debug("END setRules( List<IRule> rules)");
	}

	@Override
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

	@Override
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

	@Override
	public IDecisionTable down(final ICondition condition) {
		final IDecisionTable iDecisionTable;

		final int index = tableConditions.indexOf(condition);

		if ((index > -1) && (index < tableConditions.size() - 1)) {
			final List<ICondition> newTableConditions = new ArrayList<>(tableConditions);

			// otherwise, the condition does not exist,
			// or the condition is the last one.
			final ICondition otherCondition = tableConditions.get(index + 1);
			newTableConditions.set(index, otherCondition);
			newTableConditions.set(index + 1, condition);

			final List<IRule> newRules = new ArrayList<>(rules);

			Collections.sort(newRules, new RuleComparator(newTableConditions));

			iDecisionTable = createDecisionTable(newTableConditions, actions, newRules, groups);

			Collections.sort(newRules, new RuleComparator(newTableConditions));
		} else {
			iDecisionTable = this;
		}

		fire();

		return iDecisionTable;
	}

	@Override
	public IDecisionTable up(final ICondition condition) {
		final IDecisionTable iDecisionTable;

		final int index = tableConditions.indexOf(condition);

		if ((index > 0) && (index < tableConditions.size())) {

			final List<ICondition> newTableConditions = new ArrayList<>(tableConditions);

			// otherwise, the condition does not exist,
			// or the condition is the first one.
			final ICondition otherCondition = newTableConditions.get(index - 1);
			newTableConditions.set(index, otherCondition);
			newTableConditions.set(index - 1, condition);

			final List<IRule> newRules = new ArrayList<>(rules);

			Collections.sort(newRules, new RuleComparator(newTableConditions));

			iDecisionTable = createDecisionTable(newTableConditions, actions, newRules, groups);
		} else {
			iDecisionTable = this;
		}

		fire();

		return iDecisionTable;
	}

	@Override
	public IDecisionTable getSubtable(final List<ICondition> conditions) {
		final List<IAction> releavantActions = new ArrayList<>(actions);

		return getSubtable(conditions, releavantActions);
	}

	@Override
	public IDecisionTable getSubtable(final List<ICondition> conditions, final List<IAction> releavantActions) {
		final List<IRule> newRules = new ArrayList<>();
		final List<IRule> tmpRules = new ArrayList<>();
		final List<IGroup> newGroups = new ArrayList<>();

		final int size = conditions.size();

		if (size > 0) {
			final Stack<ICondition> conditionStack = new Stack<ICondition>();

			for (int i = size - 1; i >= 0; i--) {
				conditionStack.push(conditions.get(i));
			}

			// Create all rules possible.
			createRules(tmpRules, conditionStack, new HashMap<ICondition, IConditionValue>());

			final Map<String, Map<IAction, IValue>> condition2actions = new HashMap<>();

			for (final IRule rule : rules) {
				String conditionKey = ":";
				for (final ICondition condition : conditionStack) {
					conditionKey = conditionKey + rule.getConditionValue(condition).toString() + ":";
				}

				final Map<IAction, IValue> actionMap = condition2actions.get(conditionKey);
				final Map<IAction, IValue> actions3 = getActions(rule, releavantActions);
				if (actionMap == null) {
					condition2actions.put(conditionKey, actions3);
				} else {
					for (final Map.Entry<IAction, IValue> entry : actions3.entrySet()) {
						final IAction key = entry.getKey();
						final IValue value = entry.getValue();
						final IValue iValue = actionMap.get(key);
						if (!iValue.equals(value)) {
							actionMap.put(key, value.getInitialValue());
						}
					}

				}
			}

			for (final IRule rule : tmpRules) {
				String conditionKey = ":";
				for (final ICondition condition : conditionStack) {
					conditionKey = conditionKey + rule.getConditionValue(condition).toString() + ":";
				}

				newRules.add(rule.addActions(condition2actions.get(conditionKey)));
			}
		}

		return createDecisionTable(conditions, releavantActions, newRules, newGroups);
	}

	private Map<IAction, IValue> getActions(final IRule rule, final List<IAction> actions2) {
		final Map<IAction, IValue> action2Value = new HashMap<>();
		for (final IAction action : actions2) {
			action2Value.put(action, rule.getActionValue(action));
		}
		return action2Value;
	}

	// @Override
	// @Override
	// public IDecisionTable getSubtable(final int[] requestedConditions, final
	// int[] requestedActions) {
	// final IDecisionTable subtable = new DecisionTable();
	//
	// {
	// for (final int element : requestedConditions) {
	// subtable.add(getConditions().get(element));
	// }
	// }
	//
	// {
	// for (final int element : requestedActions) {
	// subtable.add(getActions().get(element));
	// }
	// }
	//
	// addActionValuesToSubtable(subtable);
	//
	// return subtable;
	// }

	// private void addActionValuesToSubtable(final IDecisionTable subtable) {
	// // subtable.split();
	//
	// {
	// for (final IRule subtableRule : subtable.getRules()) {
	// final Map<ICondition, IConditionValue> conditions =
	// subtableRule.getConditions();
	//
	// final List<IRule> interestingRules = getRules(conditions);
	//
	// for (final IAction action : subtableRule.getActions().keySet()) {
	// IValue value = null;
	// for (final IRule rule : interestingRules) {
	// final IValue value2 = rule.getActionValue(action);
	//
	// if (value == null) {
	// value = value2;
	// } else if (!value.equals(value2)) {
	// value = BinaryActionValue.initialValue();
	// break;
	// }
	// }
	//
	// if (value == null) {
	// value = BinaryActionValue.initialValue();
	// }
	//
	// subtableRule.setActionValue(action, value);
	// }
	// }
	// }
	// }

	@Override
	public List<IRule> getRules(final Map<ICondition, IConditionValue> certainConditions) {
		final List<IRule> certainConditionRules = new ArrayList<IRule>();

		for (final IRule rule : rules) {
			boolean sameConditions = true;
			for (final Map.Entry<ICondition, IConditionValue> conditionEntry : certainConditions.entrySet()) {
				if (!conditionEntry.getValue().equals(rule.getConditionValue(conditionEntry.getKey()))) {
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

	@Override
	public IDecisionTable remove(final IAction action) {
		final List<IRule> newRules = new ArrayList<>();

		for (final IRule rule : rules) {
			newRules.add(rule.remove(action));
		}

		final List<IAction> newActions = new ArrayList<>(actions);

		newActions.remove(action);

		// action.removePropertyChangeListener(this);
		fire();

		return createDecisionTable(newRules, newActions);
	}

	@Override
	public void remove(final ICondition condition) {
		final List<IRule> newRules = new ArrayList<IRule>();

		while (!rules.isEmpty()) {
			final IRule rule1 = rules.remove(0);
			rule1.remove(condition);
			final IRule rule2 = findRule(rule1);

			if (rule2 != null) {
				rules.remove(rule2);
				for (final IAction action : actions) {
					if (!rule1.getActionValue(action).equals(rule2.getActionValue(action))) {
						// Different values so make them unknown.
						final IValue value = rule1.getActionValue(action).getInitialValue();
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

	@Override
	public void propertyChange(final PropertyChangeEvent propertyChangeEvent) {
		logger.debug("propertyChange on " + getClass().getName() + ":" + propertyChangeEvent.getPropertyName());
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

	@Override
	public String getShortDescription() {
		return shortDescription;
	}

	@Override
	public void setShortDescription(final String shortDescription) {
		final String oldShortDescription = this.shortDescription;
		this.shortDescription = shortDescription;
		// firePropertyChange(PROP_SHORT_DESCRIPTION, oldShortDescription,
		// shortDescription);
		fire(new PropChangeEvent(oldShortDescription, shortDescription));
	}

	private void fire(final PropChangeEvent propChangeEvent) {
		// TODO Auto-generated method stub

	}

	@Override
	public IDecisionTable add(final IGroup group) {
		final List<IAction> newActions = new ArrayList<>();
		final List<ICondition> newTableConditions = new ArrayList<>();
		final List<IGroup> newGroups = new ArrayList<>();
		final List<IRule> newRules;

		{
			for (final ICondition condition : tableConditions) {
				newTableConditions.add(condition);
			}

			for (final ICondition condition : group.conditions()) {
				if (!tableConditions.contains(condition)) {
					newTableConditions.add(condition);
				}
			}
		}

		{
			for (final IAction action : actions) {
				newActions.add(action);
			}

			for (final IAction action : group.actions()) {
				if (!actions.contains(action)) {
					add(action);
				}
			}
		}

		if (rules.isEmpty()) {
			// Create a single rule.
			newRules = new ArrayList<>();
			newRules.add(new Rule().addConditions(newTableConditions).addActions(newActions));
		} else {
			List<IRule> tmpRules = new ArrayList<>(rules);
			for (final ICondition condition : group.conditions()) {
				if (!tableConditions.contains(condition)) {
					final List<IRule> newTmpRules = new ArrayList<>(tmpRules);
					for (final IRule rule : tmpRules) {
						newTmpRules.add(rule.addCondition(condition));
					}
					tmpRules = newTmpRules;
				}
			}
			newRules = tmpRules;
		}

		for (final IGroup someGroup : groups) {
			newGroups.add(someGroup);
		}

		newGroups.add(group);

		return createDecisionTable(newTableConditions, newActions, newRules, newGroups);
	}

	@Override
	public List<IGroup> getGroups() {
		return groups;
	}

	public void setGroups(final List<IGroup> groups) {
		groups.addAll(groups);

		logger.debug("END setConditionGroups(List<IGroup> groups)");
	}

	@Override
	public boolean hasGroups() {
		return groups.size() > 0;
	}

	@Override
	public IDecisionTable addBinaryAction(final String action) {
		return add(new BinaryAction(action));
	}

	@Override
	public IDecisionTable addBinaryActions(final String... actions) {
		final List<IAction> newActions = new ArrayList<>();
		for (final String action : actions) {
			newActions.add(new BinaryAction(action));
		}

		return addActions(newActions);
	}

	@Override
	public IDecisionTable addBinaryCondition(final String condition) {
		return add(new BinaryCondition(condition));
	}

	@Override
	public IDecisionTable addBinaryConditions(final String... conditions) {
		final List<ICondition> newTableConditions = new ArrayList<>();

		final List<IRule> newRules;

		for (final String condition : conditions) {
			if (tableConditions.contains(condition)) {
				throw new DTException("You cannot add a condition twice");
			}

			// 1. Add new condition to our list.
			newTableConditions.add(new BinaryCondition(condition));
		}

		// 2. For each rule, add condition with default value.
		if (rules.size() == 0) {
			newRules = splitRules(newTableConditions);
		} else {
			newRules = addToAllRules(newTableConditions);
		}

		newTableConditions.addAll(tableConditions);

		return createDecisionTable(newRules, actions, newTableConditions);
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
	public IDecisionTable setActionValues(final IRule rule, final IValue... actions) {
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
	public IDecisionTable setActionValues(final IValue value, final IAction action) {
		for (final IRule rule : rules) {
			rule.setActionValue(action, value);
		}

		return this;
	}

	@Override
	public boolean sameConditions(final IRule rule, final IConditionValue... conditionValues) {
		if (conditionValues.length != this.tableConditions.size()) {
			throw new DTException("Incorrect number of conditions");
		}

		for (int index = 0; index < conditionValues.length; index++) {
			if (!rule.getConditionValue(getConditions().get(index)).equals(conditionValues[index])) {
				return false;
			}
		}

		return true;
	}

	@Override
	public boolean sameConditions(final IRule rule, final List<IConditionValue> conditionValues) {
		if (conditionValues.size() != this.tableConditions.size()) {
			throw new DTException("Incorrect number of conditions");
		}

		for (int index = 0; index < conditionValues.size(); index++) {
			if (!rule.getConditionValue(getConditions().get(index)).equals(conditionValues.get(index))) {
				return false;
			}
		}

		return true;
	}

	@Override
	public IDecisionTable setDefaultActionValue(final BinaryActionValue binaryActionValue) {
		this.defaultActionValue = binaryActionValue;

		return this;
	}

	@Override
	public IRule getRule(final List<IConditionValue> conditions) {
		if (conditions.size() != this.tableConditions.size()) {
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
	public List<IConditionValue> getConditionValues(final IRule irule) {
		final List<IConditionValue> conditionValues = new ArrayList<>();

		this.tableConditions.stream().forEach(condition -> {
			conditionValues.add(irule.getConditionValue(condition));
		});

		return conditionValues;
	}

	@Override
	public IDecisionTable getSubtable(final String... conditions) {
		final List<ICondition> conditionList = new ArrayList<>();

		for (final String condition : conditions) {
			final ICondition someCondition = getCondition(condition);

			conditionList.add(someCondition);
		}

		return getSubtable(conditionList);
	}

	private ICondition getCondition(final String condition) {
		for (final ICondition c : tableConditions) {
			if (c.getShortDescription().equals(condition)) {
				return c;
			}
		}

		return null;
	}

	@Override
	public boolean validate() {
		for (final IRule rule : rules) {
			if (!rule.getConditions().keySet().equals(new HashSet<>(getConditions()))) {
				return false;
			}
		}

		return true;
	}
}
