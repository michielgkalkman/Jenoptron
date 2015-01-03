package jdt.core.binary;

import java.util.List;

import jdt.core.events.PropChangeEvent;
import jdt.icore.IAction;
import jdt.icore.IValue;

import com.google.common.eventbus.EventBus;

public class BinaryAction extends jdt.core.Model implements IAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1617158798961613116L;
	private String shortDescription;
	private EventBus eventBus;

	public BinaryAction() {
		this("BinaryAction");
	}

	public BinaryAction(final String shortDescription) {
		super();

		this.shortDescription = shortDescription;
	}

	public IAction deepcopy() {
		return this;
	}

	public IValue getDefaultValue() {
		return BinaryActionValue.defaultValue();
	}

	public IValue getUnknownValue() {
		return BinaryActionValue.initialValue();
	}

	public List<IValue> getSelectableValues() {
		return BinaryActionValue.selectableValues();
	}

	@Override
	public String toString() {
		return getShortDescription();
	}

	public IValue parse(final String string) {
		return BinaryActionValue.parse(string);
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

	@Override
	public void addPropertyChangeListener(final EventBus eventBus) {
		// assert this.eventBus == null || eventBus == this.eventBus;
		this.eventBus = eventBus;
	}
}
