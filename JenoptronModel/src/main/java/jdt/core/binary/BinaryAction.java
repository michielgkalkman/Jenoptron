package jdt.core.binary;

import java.util.List;

import jdt.icore.IAction;
import jdt.icore.IValue;

public class BinaryAction extends jdt.core.Model implements IAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1617158798961613116L;
	private String shortDescription;

	public BinaryAction() {
		this("BinaryAction");
	}

	public BinaryAction(final String shortDescription) {
		super();

		this.shortDescription = shortDescription;
	}

	@Override
	public IAction deepcopy() {
		return this;
	}

	@Override
	public IValue getDefaultValue() {
		return BinaryActionValue.defaultValue();
	}

	@Override
	public IValue getUnknownValue() {
		return BinaryActionValue.initialValue();
	}

	@Override
	public List<IValue> getSelectableValues() {
		return BinaryActionValue.selectableValues();
	}

	@Override
	public String toString() {
		return getShortDescription();
	}

	@Override
	public IValue parse(final String string) {
		return BinaryActionValue.parse(string);
	}

	@Override
	public String getShortDescription() {
		return shortDescription;
	}

	@Override
	public void setShortDescription(final String shortDescription) {
		final String oldShortDescription = this.shortDescription;
		this.shortDescription = shortDescription;
	}
}
