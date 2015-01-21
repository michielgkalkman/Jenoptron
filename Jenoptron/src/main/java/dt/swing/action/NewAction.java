/*
 * Created on Aug 16, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dt.swing.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import dt.swing.DTDesktop;

/**
 * @author Michiel Kalkman
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class NewAction extends AbstractAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7927213678179149394L;
	private final DTDesktop desktop;
	
	public NewAction( final DTDesktop desktop) {
		this.desktop = desktop;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent e) {
		desktop.openNewWindow();
	}

}
