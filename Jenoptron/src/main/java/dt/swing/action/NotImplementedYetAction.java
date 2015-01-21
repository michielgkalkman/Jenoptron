/*
 * Created on Aug 5, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dt.swing.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import dt.swing.DTDesktop;
import dt.swing.DTTable;
import dt.swing.fw.Dialogs;

/**
 * @author Michiel Kalkman
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NotImplementedYetAction extends AbstractAction {
    private static final long serialVersionUID = 761374236975419655L;
	private DTTable table;
	private DTDesktop desktop;
	
	
	public NotImplementedYetAction( final DTDesktop desktop) {
		super();
		this.desktop = desktop;
	}

	/**
	 * 
	 */
	public NotImplementedYetAction(final DTTable table) {
		super();
		this.table = table;
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent e) {
		if( table == null) {
			Dialogs.showNotYetImplemented( desktop);
		} else {
			Dialogs.showNotYetImplemented( table);
		}
	}

}
