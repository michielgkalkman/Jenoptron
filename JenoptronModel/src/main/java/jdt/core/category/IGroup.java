package jdt.core.category;

import java.io.Serializable;

import jdt.icore.IAction;
import jdt.icore.ICondition;
import jdt.icore.IRule;

public interface IGroup extends Serializable {
	boolean isValid( final IRule rule);
	Iterable< ICondition> conditions();
	Iterable< IAction> actions();
	String getShortDescription();
	String getShortDescription( final String groupMemberShortDescription);
}
