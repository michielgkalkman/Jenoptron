package jdt.core.category;

import jdt.icore.IAction;
import jdt.icore.ICondition;
import jdt.icore.IValue;

public interface IImplyGroup extends IGroup {
	void add( final ICondition condition);
	void add( final IAction action);
	void add( final ICondition condition, final IValue value);
	void add( final IAction action, final IValue value);
	void implies( final ICondition condition);
	void implies( final IAction action);
	void implies( final ICondition condition, final IValue value);
	void implies( final IAction action, final IValue value);
}
