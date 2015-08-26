package jdt.core.category;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import jdt.core.binary.BinaryConditionValue;
import jdt.icore.IAction;
import jdt.icore.ICondition;
import jdt.icore.IRule;

public class ExclusiveConditionsGroup implements IExclusiveConditionGroup {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2141178139072446295L;
	private final List<ICondition> mutualExclusiveConditions;
	private final String shortDescription;

	ExclusiveConditionsGroup() {
		this("Deze constructor niet gebruiken !");
	}

	public ExclusiveConditionsGroup(final String shortDescription) {
		this(shortDescription, null);
	}

	public ExclusiveConditionsGroup(final String shortDescription, final List<ICondition> mutualExclusiveConditions) {
		super();

		this.shortDescription = shortDescription;
		this.mutualExclusiveConditions = mutualExclusiveConditions;
	}

	@Override
	public ExclusiveConditionsGroup add(final ICondition... conditions) {
		final List<ICondition> newMutualExclusiveConditions = new ArrayList<>();
		if (mutualExclusiveConditions != null) {
			newMutualExclusiveConditions.addAll(mutualExclusiveConditions);
		}
		for (final ICondition condition : conditions) {
			newMutualExclusiveConditions.add(condition);
		}
		return new ExclusiveConditionsGroup(shortDescription, newMutualExclusiveConditions);
	}

	@Override
	public boolean isValid(final IRule rule) {
		int countYes = 0;
		int countIrrelevant = 0;

		for (final ICondition condition : mutualExclusiveConditions) {
			if (rule.getConditionValue(condition).equals(BinaryConditionValue.YES)) {
				countYes++;
			} else if (rule.getConditionValue(condition).equals(BinaryConditionValue.IRRELEVANT)) {
				countIrrelevant++;
			}
		}

		return countYes == 1 || countIrrelevant == mutualExclusiveConditions.size();
	}

	@Override
	public Iterable<ICondition> conditions() {
		return mutualExclusiveConditions;
	}

	@Override
	public Iterable<IAction> actions() {
		return Collections.EMPTY_LIST;
	}

	public void setActionValues(final IRule rule) {
		// No actions, so no action here.
	}

	@Override
	public String getShortDescription() {
		return shortDescription;
	}

	public ExclusiveConditionsGroup setShortDescription(final String shortDescription) {
		final ExclusiveConditionsGroup exclusiveConditionsGroup;
		if (StringUtils.equals(shortDescription, this.shortDescription)) {
			exclusiveConditionsGroup = this;
		} else {
			exclusiveConditionsGroup = new ExclusiveConditionsGroup(shortDescription, mutualExclusiveConditions);
		}
		return exclusiveConditionsGroup;
	}

	@Override
	public String getShortDescription(final String groupMemberShortDescription) {
		return getShortDescription() + ":" + groupMemberShortDescription;
	}
}
