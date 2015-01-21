/*
 * Created on Jul 28, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dt.swing.action;

import java.awt.event.ActionEvent;

import dt.swing.DTDesktop;
import dt.swing.DTTable;
import dt.swing.fw.Dialogs;

/**
 * @author Michiel Kalkman
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DeleteAction extends BaseAction {
    private static final long serialVersionUID = 3394656664033789836L;
    
    public DeleteAction( final DTTable table) {
    	super(table);
    }
    public DeleteAction( final DTDesktop desktop) {
    	super(desktop);
    }
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent e) {
		final DTTable table = getTable();
		if( table == null) {
			Dialogs.showNotYetImplemented( getDesktop());
		} else {
			table.deleteSelectedRows();
		}
	}

}
