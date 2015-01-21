package dt.swing.action;

import javax.swing.AbstractAction;

public abstract class JenoptronAction extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6511000379818346115L;

	public abstract boolean isApplicable();
}
