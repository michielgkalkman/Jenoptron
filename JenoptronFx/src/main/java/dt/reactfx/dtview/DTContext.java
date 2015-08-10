package dt.reactfx.dtview;

import jdt.icore.IAction;
import jdt.icore.ICondition;
import jdt.icore.IRule;

public class DTContext {
	private final ICondition iCondition;
	private final IAction iAction;
	private final IRule iRule;
	private final int cellX;
	private final int cellY;

	public DTContext(final int cellX, final int cellY, final IRule iRule, final ICondition iCondition,
			final IAction iAction) {
		super();
		this.cellX = cellX;
		this.cellY = cellY;
		this.iRule = iRule;
		this.iCondition = iCondition;
		this.iAction = iAction;
	}

	public final ICondition getiCondition() {
		return iCondition;
	}

	public final IAction getiAction() {
		return iAction;
	}

	public IRule getiRule() {
		return iRule;
	}

	public int getCellX() {
		return cellX;
	}

	public int getCellY() {
		return cellY;
	}
}
