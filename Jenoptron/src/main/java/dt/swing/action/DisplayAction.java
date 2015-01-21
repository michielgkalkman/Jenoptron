/*
 * Created on Aug 5, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dt.swing.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.SwingUtilities;

import org.taHjaj.wo.swingk.fw.GUIUtils;

import dt.swing.DTDesktop;
import dt.swing.DTMain;
import dt.swing.DTTable;
import dt.swing.display.DTDisplay;
import dt.swing.display.DTDisplayBean;

/**
 * @author Michiel Kalkman
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DisplayAction extends AbstractAction {
    private static final long serialVersionUID = 761374236975419655L;
	private final DTTable table;
	private final DTDisplayBean displayBean;

	/**
	 * 
	 */
	public DisplayAction(final DTTable table) {
		super();
		this.table = table;
		this.displayBean = new DTDisplayBean( table.getDecisionTable());
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent e) {
		final DTDesktop desktop = DTMain.getMainFrame().getDesktop();
		SwingUtilities.invokeLater(new Runnable() {
		        public void run()
		        {
		    		final DTDisplay frame = new DTDisplay( displayBean);

		    		desktop.add( frame, 0);	
		    		GUIUtils.centerWithinDesktop( frame);
		    		frame.setSize( frame.getMaximumSize());

		    		frame.pack();
		    		frame.setVisible( true);
		    		frame.transferFocus();
		        }
		      });

	}

	@Override
	public boolean isEnabled() {
		return displayBean.isEnabled() && super.isEnabled();
	}
}
