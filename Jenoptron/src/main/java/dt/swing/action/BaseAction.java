/*
 * Created on Jul 28, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dt.swing.action;

import javax.swing.AbstractAction;

import dt.swing.DTDesktop;
import dt.swing.DTTable;

/**
 * @author Michiel Kalkman
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public abstract class BaseAction extends AbstractAction {
    private static final long serialVersionUID = 3394656664033789836L;
    private DTTable table;
    private DTDesktop desktop;
    
    public BaseAction( final DTTable table) {
    	super();
    	this.table = table;
    }
    
    public BaseAction( final DTDesktop desktop) {
    	super();
    	this.desktop = desktop;
    }

	public DTDesktop getDesktop() {
		return desktop;
	}

	public DTTable getTable() {
		return table;
	}
}
