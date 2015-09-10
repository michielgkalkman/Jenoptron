package jdt.core.category;

import java.io.Serializable;
import java.util.List;

import jdt.icore.IAction;
import jdt.icore.ICondition;
import jdt.icore.IRule;

public interface IGroup extends Serializable {
	boolean isValid(final IRule rule);

	List<ICondition> conditions();

	List<IAction> actions();

	String getShortDescription();

	String getShortDescription(final String groupMemberShortDescription);
}
