package jdt.icore;

import java.io.Serializable;
import java.util.List;

/**
 * Describes a kind of action.
 * 
 * E.g. a flag signifying whether something must be printed or not. But not a
 * flag that signifies that something must be printed (i.e. a value).
 * 
 * @author Michiel Kalkman
 * 
 */
public interface IAction extends Serializable {
	static final String CHANGE_EVENT = "CHANGE_EVENT";
	static final String PROP_SHORT_DESCRIPTION = "shortDescription";

	IAction deepcopy();

	IValue getDefaultValue();

	IValue getUnknownValue();

	List<IValue> getSelectableValues();

	String getShortDescription();

	void setShortDescription(String shortDescription);

	IValue parse(String string);
}
