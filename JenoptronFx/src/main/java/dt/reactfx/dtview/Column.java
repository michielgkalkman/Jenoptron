package dt.reactfx.dtview;

import java.util.ArrayList;
import java.util.List;

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
		final List<Cell> newCells = new ArrayList<>();

		cells.stream().forEach(cell -> {
			newCells.add(cell.toggleSelect(newCell));
		});

		return new Column(width, newCells);
	}

}
