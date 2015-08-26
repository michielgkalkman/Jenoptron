package jdt.core.binary;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import jdt.core.Model;
import jdt.icore.ICondition;
import jdt.icore.IConditionValue;

/**
 * A description of a binary condition.
 * 
 * E.g. A flag to search for a category of items. BUT NOT THE VALUE. So, it does
 * not represent whether the flag to search for a category of items is set or
 * not.
 * 
 * @author Michiel Kalkman
 * 
 */

public class BinaryCondition extends Model implements ICondition {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5040711107026041534L;
	private final String shortDescription;

	@Override
	public BinaryCondition deepcopy() {
		return this;
	}

	public BinaryCondition() {
		this("BinaryCondition");
	}

	public BinaryCondition(final String shortDescription) {
		super();

		this.shortDescription = shortDescription;
	}

	@Override
	public IConditionValue getDefaultValue() {
		return BinaryConditionValue.defaultValue();
	}

	@Override
	public IConditionValue getIrrelevantValue() {
		return BinaryConditionValue.initialValue();
	}

	@Override
	public List<IConditionValue> getPossibleValues() {
		return BinaryConditionValue.getPossibleValues();
	}

	@Override
	public String toString() {
		return getShortDescription();
	}

	@Override
	public String getShortDescription() {
		return shortDescription;
	}

	@Override
	public BinaryCondition setShortDescription(final String shortDescription) {
		final BinaryCondition binaryCondition;
		if (StringUtils.equals(shortDescription, this.shortDescription)) {
			binaryCondition = this;
		} else {
			binaryCondition = new BinaryCondition(shortDescription);
		}
		return binaryCondition;
	}
}
