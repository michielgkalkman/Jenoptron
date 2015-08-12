package dt.reactfx.dtview;

import jdt.icore.IAction;
import jdt.icore.ICondition;

public class DTContext {
	private final int cellX;
	private final int cellY;
	private final Cell cell;

	public DTContext(final int cell_x, final int cell_y, final Cell cell) {
		cellX = cell_x;
		cellY = cell_y;
		this.cell = cell;
	}

	public final ICondition getiCondition() {
		return getCell().getCondition();
	}

	public final IAction getiAction() {
		return getCell().getAction();
	}

	public int getCellX() {
		return cellX;
	}

	public int getCellY() {
		return cellY;
	}

	public Cell getCell() {
		return cell;
	}
}
