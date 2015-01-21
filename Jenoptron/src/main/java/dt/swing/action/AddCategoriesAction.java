/*
 * Created on Aug 5, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dt.swing.action;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import org.taHjaj.wo.swingk.fw.GUIUtils;
import org.taHjaj.wo.swingk.util.NXUtil;

import dt.swing.DTDesktop;
import dt.swing.DTMain;
import dt.swing.DTTable;
import dt.swing.categories.DTCategoriesBean;
import dt.swing.categories.DTCategoriesFrame;

/**
 * @author Michiel Kalkman
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class AddCategoriesAction extends AbstractAction {
    private static final long serialVersionUID = 761374236975419655L;
	private final DTTable table;

	/**
	 * 
	 */
	public AddCategoriesAction(final DTTable table) {
		super();
		this.table = table;
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent e) {
		
		final DTDesktop desktop = DTMain.getMainFrame().getDesktop();
		NXUtil.invokeLater(new Runnable() {
		        public void run()
		        {
		    		final DTCategoriesFrame categoriesFrame = new DTCategoriesFrame(
		        			new DTCategoriesBean( table.getDecisionTable()));
		        	
		    		desktop.add( categoriesFrame, 0);	
		    		GUIUtils.centerWithinDesktop( categoriesFrame);
		    		categoriesFrame.setSize( categoriesFrame.getMaximumSize());

		    		categoriesFrame.pack();
		    		categoriesFrame.setVisible( true);
		    		categoriesFrame.transferFocus();
		        }
		      });

	}

}
