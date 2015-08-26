package jdt.core.category;

import jdt.icore.ICondition;

public interface IExclusiveConditionGroup extends IGroup {
	IExclusiveConditionGroup add(final ICondition... conditions);
}
