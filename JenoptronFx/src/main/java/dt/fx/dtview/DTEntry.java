package dt.fx.dtview;

import jdt.icore.ICondition;
import jdt.icore.IDecisionTable;

public class DTEntry {

	private final IDecisionTable iDecisionTable;
	private final ICondition condition;

	public DTEntry(final IDecisionTable iDecisionTable, final ICondition condition) {
		this.iDecisionTable = iDecisionTable;
		this.condition = condition;
	}

	public String getLabelText() {
		return getCondition().getShortDescription();
	}

	public IDecisionTable getiDecisionTable() {
		return iDecisionTable;
	}

	public ICondition getCondition() {
		return condition;
	}

}
