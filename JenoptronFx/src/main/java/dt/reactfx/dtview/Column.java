package dt.reactfx.dtview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jdt.icore.IAction;
import jdt.icore.ICondition;

public class Column {
	private final double width;
	private final List<Cell> cells;

	public Column(final double width) {
		this.width = width;
		cells = new ArrayList<>();
	}

	private Column(final double width, final List<Cell> cells) {
		this.width = width;
		this.cells = cells;
	}

	public double getWidth() {
		return width;
	}

	public void addCell(final Cell cell) {
		getCells().add(cell);
	}

	public List<Cell> getCells() {
		return cells;
	}

	public Column enlarge(final double factor) {
		final List<Cell> newCells = new ArrayList<>();

		cells.stream().forEach(cell -> {
			newCells.add(cell.enlarge(factor));
		});

		return new Column(width * factor, newCells);
	}

	public Column toggleSelect(final Cell newCell) {
		final Column newColumn;
		if (cells.stream().anyMatch(cell -> cell.equals(newCell))) {
			final List<Cell> newCells = new ArrayList<>();
			cells.stream().forEach(cell -> {
				newCells.add(cell.toggleSelect(newCell));
			});
			newColumn = new Column(width, Collections.unmodifiableList(newCells));
		} else {
			newColumn = this;
		}

		return newColumn;
	}

	public boolean isAnyActionSelected() {
		return cells.stream().anyMatch(cell -> cell.isSelectedAction());
	}

	public boolean isAnySelected() {
		return cells.stream().anyMatch(cell -> cell.isSelected());
	}

	public Column setSelected(final Cell cell, final boolean selected) {
		final Column newColumn;
		final IAction action = cell.getAction();
		if (action == null) {
			final ICondition condition = cell.getCondition();

			if (condition == null) {
				newColumn = this;
			} else {
				final List<Cell> newCells = new ArrayList<>();

				cells.stream().forEach(c -> {
					if (condition.equals(c.getCondition())) {
						newCells.add(c.setSelected(selected));
					} else {
						newCells.add(c);
					}
				});

				newColumn = new Column(width, Collections.unmodifiableList(newCells));
			}
		} else {
			final List<Cell> newCells = new ArrayList<>();

			cells.stream().forEach(c -> {
				if (action.equals(c.getAction())) {
					newCells.add(c.setSelected(selected));
				} else {
					newCells.add(c);
				}
			});
			newColumn = new Column(width, Collections.unmodifiableList(newCells));
		}

		return newColumn;
	}

	public Column replace(final Cell cell, final Cell newCell) {
		final Column newColumn;
		final List<Cell> newCells = new ArrayList<>();
		cells.stream().forEach(someCell -> {
			newCells.add(someCell.replace(cell, newCell));
		});
		newColumn = new Column(width, Collections.unmodifiableList(newCells));
		return newColumn;
	}

	public Column clearAllSelected() {
		final Column newColumn;

		if (isAnySelected()) {
			final List<Cell> newCells = new ArrayList<>();
			cells.stream().forEach(cell -> {
				newCells.add(cell.setSelected(false));
			});
			newColumn = new Column(width, Collections.unmodifiableList(newCells));
		} else {
			newColumn = this;
		}
		return newColumn;
	}

	public Column setDragged(final ICondition condition) {
		final Column newColumn;

		if (cells.stream().anyMatch(cell -> cell.getCondition() != null && cell.getCondition().equals(condition))) {
			final List<Cell> newCells = new ArrayList<>();
			cells.stream().forEach(cell -> {
				if (condition.equals(cell.getCondition())) {
					newCells.add(cell.setDragged());
				} else {
					newCells.add(cell);
				}
			});
			newColumn = new Column(width, Collections.unmodifiableList(newCells));
		} else {
			newColumn = this;
		}

		return newColumn;
	}

	public Column setDragged(final IAction action) {
		final Column newColumn;

		if (cells.stream().anyMatch(cell -> cell.getAction() != null && cell.getAction().equals(action))) {
			final List<Cell> newCells = new ArrayList<>();
			cells.stream().forEach(cell -> {
				if (action.equals(cell.getAction())) {
					newCells.add(cell.setDragged());
				} else {
					newCells.add(cell);
				}
			});
			newColumn = new Column(width, Collections.unmodifiableList(newCells));
		} else {
			newColumn = this;
		}

		return newColumn;
	}

	public Column setSelected(final ICondition condition, final boolean fSelected) {
		final Column newColumn;

		if (cells.stream().anyMatch(cell -> cell.getCondition() != null && cell.getCondition().equals(condition))) {
			final List<Cell> newCells = new ArrayList<>();
			cells.stream().forEach(cell -> {
				if (condition.equals(cell.getCondition())) {
					newCells.add(cell.setSelected(fSelected));
				} else {
					newCells.add(cell);
				}
			});
			newColumn = new Column(width, Collections.unmodifiableList(newCells));
		} else {
			newColumn = this;
		}

		return newColumn;
	}

	public Column setSelected(final IAction action, final boolean fSelected) {
		final Column newColumn;

		if (cells.stream().anyMatch(cell -> cell.getAction() != null && cell.getAction().equals(action))) {
			final List<Cell> newCells = new ArrayList<>();
			cells.stream().forEach(cell -> {
				if (action.equals(cell.getAction())) {
					newCells.add(cell.setSelected(fSelected));
				} else {
					newCells.add(cell);
				}
			});
			newColumn = new Column(width, Collections.unmodifiableList(newCells));
		} else {
			newColumn = this;
		}

		return newColumn;
	}
}
