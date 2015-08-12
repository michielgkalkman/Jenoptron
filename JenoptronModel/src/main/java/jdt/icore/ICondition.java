package jdt.icore;

import java.io.Serializable;
import java.util.List;

import jdt.core.category.IGroup;

/**
 * A description of a condition.
 * 
 * E.g. A flag to search for a category of items. BUT NOT THE VALUE. So, it does
 * not represent whether the flag to search for a category of items is set or
 * not.
 * 
 * @author Michiel Kalkman
 * 
 */
public interface ICondition extends Serializable {
	static final String CHANGE_EVENT = "CHANGE_EVENT";
	static final String PROP_SHORT_DESCRIPTION = "shortDescription";

	IConditionValue getIrrelevantValue();

	IConditionValue getDefaultValue();

	ICondition deepcopy();

	List<IConditionValue> getPossibleValues();

	String getShortDescription();

	void setGroup(IGroup group);

	void setShortDescription(String shortDescription);
}
