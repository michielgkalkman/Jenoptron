/*
 * Created on Aug 5, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dt.swing.action;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import dt.generators.GeneratorFactorySPI;
import dt.generators.TextGenerator;
import dt.swing.DTTable;

/**
 * @author Michiel Kalkman
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class CopyDefaultTextAction extends AbstractAction {
    private static final long serialVersionUID = 761374236975419655L;
	private final DTTable table;
	/**
	 *
	 */
	public CopyDefaultTextAction(final DTTable table) {
		super();
		this.table = table;
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent actionEvent) {
//		if (!table.getColumnSelectionAllowed()
//	            && table.getRowSelectionAllowed()) {
//	        // Row selection is enabled
//	        // Get the indices of the selected rows
//	        table.getSelectedRows();
//	    } else {
//	    	// Copy: everything.
//	    }

		final TextGenerator textGenerator = GeneratorFactorySPI.getAnyTextGenerator();

		final String text = textGenerator.getText( table.getDecisionTable());

		final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		final Transferable transferable = new StringSelection( text);
		clipboard.setContents( transferable, null);
	}

}
