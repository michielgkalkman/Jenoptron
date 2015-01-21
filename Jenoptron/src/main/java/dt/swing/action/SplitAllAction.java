/*
 * Created on Aug 2, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dt.swing.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import dt.swing.DTTable;

/**
 * @author Michiel Kalkman
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class SplitAllAction extends AbstractAction {
    private static final long serialVersionUID = -161961762172374241L;
	private final DTTable table;
	
	public SplitAllAction( final DTTable table) {
		super();
		this.table = table;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent e) {
		table.getDecisionTable().split();
		table.refresh();
	}

}
