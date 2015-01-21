/*
 * Created on Jul 28, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dt.swing;

import java.awt.GridLayout;
import java.beans.PropertyChangeListener;

import org.taHjaj.wo.swingk.KPanel;

/**
 * @author Michiel Kalkman
 *
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public abstract class DTPanel extends KPanel implements PropertyChangeListener {
	private static final long serialVersionUID = 5405631376452046977L;

	public DTPanel() {
		super(new GridLayout(1, 0));
	}
}
