package jdt.icore;

import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import jdt.core.binary.BinaryActionValue;
import jdt.core.category.IGroup;

/**
 * A base interface use for decision tables.
 * 
 * Actually it forms an interface for classes that manage:
 * <ul>
 * <li>a template for a single rule</li>
 * <li>instances of this single rule</li>
 * </ul>
 * 
 * This single rule consists of conditions c1..cN and actions a1..aN.
 * 
 * An instance consists of values for these condition and actions.
 * 
 * The class knows:
 * <ul>
 * <li>the order of the conditions</li>
 * <li>the order of the actions</li>
 * <li>the instances of the rules</li>
 * </ul>
 * 
 * @author Michiel Kalkman
 * 
 */
public interface IDecisionTable extends CUDDecisionTable, PropertyChangeListener, Serializable, Cloneable, IObject {
	static final String PROP_SHORT_DESCRIPTION = "shortDescription";

	/**
	 * Add a condition.
	 * 
	 * This will add a condition to the template.
	 * 
	 * @param condition
	 * @param defaultValue
	 * @return
	 */
	IDecisionTable add(final ICondition newConditions);

	IDecisionTable add(final ICondition... newConditions);

	IDecisionTable add(final Collection<? extends ICondition> newConditions);

	IDecisionTable add(final IAction newAction);

	IDecisionTable add(final IAction... newActions);

	IDecisionTable addActions(final Collection<? extends IAction> newActions);

	IDecisionTable add(final IGroup group);

	IDecisionTable addBinaryAction(final String action);

	IDecisionTable addBinaryCondition(final String condition);

	IDecisionTable addBinaryActions(final String... action);

	IDecisionTable addBinaryConditions(final String... condition);

	/**
	 * Retrieve the rule in the decision table with the given condition values.
	 * 
	 * Note that a rule might not exist anymore after reducing or splitting.
	 * 
	 * @param conditions
	 * @return
	 */
	IRule getRule(final IConditionValue... conditions);

	/**
	 * Retrieve the rule in the decision table with the given condition values.
	 * 
	 * Note that a rule might not exist anymore after reducing or splitting.
	 * 
	 * @param conditions
	 * @return
	 */
	IRule getRule(final List<IConditionValue> conditions);

	/**
	 * Split all rules.
	 * 
	 * Disallow any IRRELEVANT conditions.
	 * 
	 * @return this.
	 */
	IDecisionTable split();

	/**
	 * Reduce to essentials.
	 * 
	 * @return
	 */
	IDecisionTable reduce();

	IDecisionTable otherwise();

	List<IRule> getRules();

	List<IRule> getRules(final Map<ICondition, IConditionValue> conditions);

	List<IRule> getAllRules();

	Iterable<IRule> getLegalRulesIterable();

	List<ICondition> getConditions();

	List<IAction> getActions();

	List<IGroup> getGroups();

	boolean hasGroups();

	void setRules(final List<IRule> rules);

	IDecisionTable insert(final int position, final IAction action);

	IDecisionTable insert(final int position, final ICondition condition);

	IDecisionTable remove(final IAction action);

	void remove(final ICondition condition);

	boolean isValid(final IRule rule);

	boolean isInvalid(final IRule rule);

	IDecisionTable up(final IAction action);

	IDecisionTable down(final IAction action);

	IDecisionTable up(final ICondition condition);

	IDecisionTable down(final ICondition condition);

	int nrConditions();

	int nrRules();

	IRule getRule(final int ruleNr);

	boolean showsAllRules();

	IDecisionTable getSubtable(final String... conditions);

	String getShortDescription();

	void setShortDescription(final String shortDescription);

	static final String CHANGE_EVENT = "CHANGE_EVENT";

	public StringBuffer simpleDump();

	boolean sameConditions(final IRule rule, final IConditionValue... conditionValues);

	IDecisionTable setActionValues(final IValue value);

	IDecisionTable setActionValues(final IValue value, final IAction action);

	IDecisionTable setActionValues(final IValue... values);

	IDecisionTable setActionValues(final IRule rule, IValue... values);

	IDecisionTable setActionValues(final IRule rule, IValue value);

	int nrActions();

	IDecisionTable setDefaultActionValue(BinaryActionValue binaryActionValue);

	List<IConditionValue> getConditionValues(IRule irule);

	boolean sameConditions(IRule rule, List<IConditionValue> conditionValues);

	IDecisionTable getSubtable(List<ICondition> conditions);

	boolean validate();

	IDecisionTable getSubtable(List<ICondition> conditions, List<IAction> releavantActions);
}
