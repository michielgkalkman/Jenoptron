package dt.fx;

import jdt.core.binary.BinaryAction;
import jdt.icore.IDecisionTable;

public class Action {

	private final IDecisionTable iDecisionTable;

	public Action(final IDecisionTable iDecisionTable) {
		this.iDecisionTable = iDecisionTable;
	}

	public void run() {
		iDecisionTable.add(new BinaryAction());
	}

}
