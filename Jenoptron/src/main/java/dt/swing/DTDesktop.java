/*
 * Created on Aug 15, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dt.swing;

import java.awt.Dimension;
import java.awt.event.MouseListener;

import javax.swing.JDesktopPane;
import javax.swing.SwingUtilities;

import jdt.core.DecisionTable;
import jdt.icore.IDecisionTable;

import org.taHjaj.wo.swingk.fw.GUIUtils;

import dt.swing.dnd.DTDesktopMouseAdapter;
import dt.swing.dnd.DTDesktopTransferHandler;
import dt.swing.util.DesktopPopupListener;

/**
 * @author Michiel Kalkman
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DTDesktop extends JDesktopPane  {
	/**
	 *
	 */
	private static final long serialVersionUID = 6602664412934625153L;

	public DTDesktop() {
		super();
		setMinimumSize( new Dimension( 300, 300));
		setPreferredSize( new Dimension( 300, 300));
		setSize( new Dimension( 300, 300));

		setTransferHandler( new DTDesktopTransferHandler( this));

		final MouseListener listener = new DTDesktopMouseAdapter();
		addMouseListener(listener);

		addPopupMenu();
	}

	private void addPopupMenu() {
		final MouseListener popupListener = new DesktopPopupListener( this);
		// add the listener specifically to the header
		addMouseListener(popupListener);
	}

	public void openNewWindow() {
		openNewWindow( new DecisionTable());
	}

	public void openNewWindow( final IDecisionTable decisionTable) {
		SwingUtilities.invokeLater(new Runnable() {
	        public void run() {
	    		final DTFrame frame = new DTFrame( decisionTable);
	    		add( frame, 0);
	    		GUIUtils.centerWithinDesktop(frame);

	    		frame.setVisible( true);
	    		frame.transferFocus();
	        }
	      });
	}
}
