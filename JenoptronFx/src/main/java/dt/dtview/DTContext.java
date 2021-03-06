package dt.dtview;

import org.taHjaj.wo.jenoptron.model.icore.IAction;
import org.taHjaj.wo.jenoptron.model.icore.ICondition;
import org.taHjaj.wo.jenoptron.model.icore.IRule;

public class DTContext {
	private final IRule iRule;
	private final ICondition iCondition;
	private final IAction iAction;

	public DTContext(final IRule iRule, final ICondition iCondition, final IAction iAction) {
		super();
		this.iRule = iRule;
		this.iCondition = iCondition;
		this.iAction = iAction;
	}

	public final IRule getiRule() {
		return iRule;
	}

	public final ICondition getiCondition() {
		return iCondition;
	}

	public final IAction getiAction() {
		return iAction;
	}
}
