package dt.reactfx.dtview;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import jdt.core.binary.BinaryActionValue;
import jdt.core.binary.BinaryConditionValue;
import jdt.icore.IAction;
import jdt.icore.ICondition;
import jdt.icore.IRule;

public class Cell {
	private final IRule iRule;
	private final IAction action;
	private final BinaryActionValue binaryActionValue;
	private final ICondition condition;
	private final BinaryConditionValue binaryConditionValue;
	private final double height;
	private final double width;
	private final String shortDescription;
	private final CellType cellType;
	private final DTView dtView;

	private final boolean fSelected;
	private final boolean fDragged;

	public Cell(final double width, final double height, final IAction action,
			final BinaryActionValue binaryActionValue, final IRule iRule, final DTView dtView,
			final CellType cellType) {
		this.width = width;
		this.height = height;
		this.action = action;
		this.binaryActionValue = binaryActionValue;
		this.iRule = iRule;
		this.dtView = dtView;
		this.cellType = cellType;
		this.condition = null;
		this.binaryConditionValue = null;
		this.shortDescription = null;
		this.fSelected = false;
		this.fDragged = false;
	}

	public Cell(final int width, final int height, final ICondition condition,
			final BinaryConditionValue binaryConditionValue, final IRule iRule, final DTView dtView,
			final CellType cellType) {
		this.width = width;
		this.height = height;
		this.iRule = iRule;
		this.cellType = cellType;
		this.action = null;
		this.binaryActionValue = null;
		this.dtView = dtView;
		this.condition = condition;
		this.binaryConditionValue = binaryConditionValue;
		this.shortDescription = null;
		this.fSelected = false;
		this.fDragged = false;
	}

	public Cell(final int width, final int height, final DTView dtView, final CellType cellType) {
		this.width = width;
		this.height = height;
		this.cellType = cellType;
		this.action = null;
		this.binaryActionValue = null;
		this.dtView = dtView;
		this.condition = null;
		this.binaryConditionValue = null;
		this.shortDescription = null;
		this.iRule = null;
		this.fSelected = false;
		this.fDragged = false;
	}

	private Cell(final double width, final double height, final IAction action,
			final BinaryActionValue binaryActionValue, final ICondition condition,
			final BinaryConditionValue binaryConditionValue, final String shortDescription, final IRule iRule,
			final DTView dtView, final CellType cellType, final boolean selected, final boolean fDragged) {
		this.width = width;
		this.height = height;
		this.action = action;
		this.binaryActionValue = binaryActionValue;
		this.condition = condition;
		this.binaryConditionValue = binaryConditionValue;
		this.iRule = iRule;
		this.dtView = dtView;
		this.shortDescription = shortDescription;
		this.cellType = cellType;
		this.fSelected = selected;
		this.fDragged = fDragged;
	}

	public Cell(final int width, final int height, final ICondition condition, final String shortDescription,
			final DTView dtView, final CellType cellType) {
		this.width = width;
		this.height = height;
		this.cellType = cellType;
		this.action = null;
		this.binaryActionValue = null;
		this.condition = condition;
		this.binaryConditionValue = null;
		this.dtView = dtView;
		this.shortDescription = shortDescription;
		this.iRule = null;
		this.fSelected = false;
		this.fDragged = false;
	}

	public Cell(final int width, final int height, final IAction action, final String shortDescription,
			final DTView dtView, final CellType cellType) {
		this.width = width;
		this.height = height;
		this.action = action;
		this.cellType = cellType;
		this.binaryActionValue = null;
		this.condition = null;
		this.binaryConditionValue = null;
		this.dtView = dtView;
		this.shortDescription = shortDescription;
		this.iRule = null;
		this.fSelected = false;
		this.fDragged = false;
	}

	public double getHeight() {
		return height;
	}

	public Cell enlarge(final double factor) {
		return new Cell(width * factor, height * factor, getAction(), binaryActionValue, condition,
				binaryConditionValue, shortDescription, iRule, dtView, cellType, fSelected, fDragged);
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

	public IRule getiRule() {
		return iRule;
	}

	public CellType getCellType() {
		return cellType;
	}

	public boolean isSelected() {
		return fSelected;
	}

	@Override
	public String toString() {
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).toString();
	}

	public Cell toggleSelect(final Cell cell) {
		final Cell newCell;
		if (this.equals(cell)) {
			newCell = new Cell(width, height, action, binaryActionValue, condition, binaryConditionValue,
					shortDescription, iRule, dtView, cellType, fSelected ^ true, fDragged);
		} else {
			newCell = this; // cell is intended to be immutable
		}
		return newCell;
	}

	public boolean isSelectedAction() {
		return action != null && fSelected;
	}

	public Cell setSelected(final boolean selected) {
		final Cell newCell;
		if (this.fSelected == selected) {
			newCell = this; // cell is intended to be immutable
		} else {
			newCell = new Cell(width, height, action, binaryActionValue, condition, binaryConditionValue,
					shortDescription, iRule, dtView, cellType, selected, fDragged);
		}
		return newCell;
	}

	public Cell setShortDescription(final String shortDescription) {
		final Cell newCell;

		if (condition != null) {
			condition.setShortDescription(shortDescription);
			newCell = new Cell(width, height, action, binaryActionValue, condition, binaryConditionValue,
					shortDescription, iRule, dtView, cellType, fSelected, fDragged);
		} else {
			newCell = this;
		}

		return newCell;
	}

	public Cell replace(final Cell cell, final Cell replaceByCell) {
		final Cell newCell;

		if (this == cell) {
			newCell = replaceByCell;
		} else {
			newCell = this;
		}

		return newCell;
	}

	public Cell setDragged() {
		final Cell newCell;
		if (fDragged) {
			newCell = this; // cell is intended to be immutable
		} else {
			newCell = new Cell(width, height, action, binaryActionValue, condition, binaryConditionValue,
					shortDescription, iRule, dtView, cellType, fSelected, true);
		}
		return newCell;
	}

	public boolean isDragged() {
		return fDragged;
	}
}
