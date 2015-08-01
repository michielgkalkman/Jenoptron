package dt.reactfx.dtview;

import java.util.ArrayList;
import java.util.List;

public class Column {

	private final double width;
	private final List<Cell> cells = new ArrayList<>();

	public Column(final double width) {
		this.width = width;
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

}
