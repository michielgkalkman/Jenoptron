package jdt.core.category;

import jdt.icore.ICondition;

public interface IExclusiveConditionGroup extends IGroup {
	void add( final ICondition... conditions);
}
