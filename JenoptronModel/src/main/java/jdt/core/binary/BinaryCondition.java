package jdt.core.binary;

import java.util.List;

import jdt.core.Model;
import jdt.core.category.IGroup;
import jdt.core.events.PropChangeEvent;
import jdt.icore.ICondition;
import jdt.icore.IConditionValue;

import com.google.common.eventbus.EventBus;

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
	private String shortDescription;
	private IGroup group;
	private EventBus eventBus;

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

	public IConditionValue getDefaultValue() {
		return BinaryConditionValue.defaultValue();
	}

	public IConditionValue getIrrelevantValue() {
		return BinaryConditionValue.initialValue();
	}

	public List<IConditionValue> getPossibleValues() {
		return BinaryConditionValue.getPossibleValues();
	}

	@Override
	public String toString() {
		return getShortDescription();
	}

	public String getShortDescription() {
		return shortDescription;
	}

	public void setShortDescription(final String shortDescription) {
		final String oldShortDescription = this.shortDescription;
		this.shortDescription = shortDescription;
		eventBus.post(new PropChangeEvent(oldShortDescription,
				oldShortDescription));
	}

	public IGroup getGroup() {
		return group;
	}

	public void setGroup(final IGroup group) {
		this.group = group;
	}

	@Override
	public void addPropertyChangeListener(final EventBus eventBus) {
		// assert this.eventBus == null || eventBus == this.eventBus;
		this.eventBus = eventBus;
	}
}
