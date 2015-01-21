/*
 * Created on Jul 31, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dt.swing.util;


import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.taHjaj.wo.swingk.util.KPopupListener;

import dt.swing.DTDesktop;
import dt.swing.action.NewAction;

/**
 * @author Michiel Kalkman
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DesktopPopupListener extends KPopupListener {
	@Override
	public void fill( final JPopupMenu popupMenu) {
		popupMenu.add( NEW);
	}

	public static final JMenuItem NEW = new JMenuItem( "New");

	public DesktopPopupListener( final DTDesktop desktop) {
		super();

		NEW.addActionListener( new NewAction( desktop));
	}
}
