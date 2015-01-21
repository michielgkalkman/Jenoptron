/*
 * Created on Aug 2, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dt.swing.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JInternalFrame;
import javax.swing.SwingUtilities;

import org.taHjaj.wo.swingk.fw.GUIUtils;

import jdt.icore.IDecisionTable;
import dt.swing.DTDesktop;
import dt.swing.DTMain;
import dt.swing.DTTable;
import dt.swing.props.DTCategoriesPanel;

/**
 * @author Michiel Kalkman
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AddRestrictionAction extends AbstractAction {
    private static final long serialVersionUID = 761374236975419655L;
	private final DTTable table;
	
	public AddRestrictionAction( final DTTable table) {
		super();
		this.table = table;
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent e) {
		final DTDesktop desktop = DTMain.getMainFrame().getDesktop();
		SwingUtilities.invokeLater(new Runnable() {
		        public void run()
		        {
		        	final IDecisionTable decisionTable = table.getDecisionTable();
		        	final JInternalFrame frame = new JInternalFrame( decisionTable.getShortDescription(), true, true, true, true);
		        	frame.add( new DTCategoriesPanel( decisionTable));

		    		desktop.add( frame, 0);	
		    		GUIUtils.centerWithinDesktop( frame);

		    		frame.pack();
		    		frame.setVisible( true);
		    		frame.transferFocus();
		        }
		      });
//
//		if( table != null) {
//			Dialogs.showNotYetImplemented( table);
//		}
	}
}
