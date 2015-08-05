package dt.reactfx.dtview;

import jdt.core.binary.BinaryActionValue;
import jdt.core.binary.BinaryConditionValue;
import jdt.icore.IAction;
import jdt.icore.ICondition;

public class Cell {
	private final IAction action;
	private final BinaryActionValue binaryActionValue;
	private final ICondition condition;
	private final BinaryConditionValue binaryConditionValue;
	private final double height;
	private final double width;
	private final String shortDescription;

	private final DTView dtView;

	public Cell(final double width, final double height, final IAction action,
			final BinaryActionValue binaryActionValue, final DTView dtView) {
		this.width = width;
		this.height = height;
		this.action = action;
		this.binaryActionValue = binaryActionValue;
		this.dtView = dtView;
		this.condition = null;
		this.binaryConditionValue = null;
		this.shortDescription = null;
	}

	public Cell(final int width, final int height, final ICondition condition,
			final BinaryConditionValue binaryConditionValue, final DTView dtView) {
		this.width = width;
		this.height = height;
		this.action = null;
		this.binaryActionValue = null;
		this.dtView = dtView;
		this.condition = condition;
		this.binaryConditionValue = binaryConditionValue;
		this.shortDescription = null;
	}

	public Cell(final int width, final int height, final DTView dtView) {
		this.width = width;
		this.height = height;
		this.action = null;
		this.binaryActionValue = null;
		this.dtView = dtView;
		this.condition = null;
		this.binaryConditionValue = null;
		this.shortDescription = null;
	}

	private Cell(final double width, final double height, final IAction action,
			final BinaryActionValue binaryActionValue, final ICondition condition,
			final BinaryConditionValue binaryConditionValue, final String shortDescription, final DTView dtView) {
		this.width = width;
		this.height = height;
		this.action = action;
		this.binaryActionValue = binaryActionValue;
		this.condition = condition;
		this.binaryConditionValue = binaryConditionValue;
		this.dtView = dtView;
		this.shortDescription = shortDescription;
	}

	public Cell(final int width, final int height, final ICondition condition, final String shortDescription,
			final DTView dtView) {
		this.width = width;
		this.height = height;
		this.action = null;
		this.binaryActionValue = null;
		this.condition = condition;
		this.binaryConditionValue = null;
		this.dtView = dtView;
		this.shortDescription = shortDescription;
	}

	public Cell(final int width, final int height, final IAction action, final String shortDescription,
			final DTView dtView) {
		this.width = width;
		this.height = height;
		this.action = action;
		this.binaryActionValue = null;
		this.condition = null;
		this.binaryConditionValue = null;
		this.dtView = dtView;
		this.shortDescription = shortDescription;
	}

	public double getHeight() {
		return height;
	}

	public Cell enlarge(final double factor) {
		return new Cell(width * factor, height * factor, getAction(), binaryActionValue, condition,
				binaryConditionValue, shortDescription, dtView);
	}

	public IAction getAction() {
		return action;
	}

	public BinaryActionValue getBinaryActionValue() {
		return binaryActionValue;
	}

	public ICondition getCondition() {
		return condition;
	}

	public BinaryConditionValue getBinaryConditionValue() {
		return binaryConditionValue;
	}

	public double getWidth() {
		return width;
	}

	public String getShortDescription() {
		return shortDescription;
	}
}
