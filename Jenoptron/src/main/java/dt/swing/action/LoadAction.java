/*
 * Created on Aug 1, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dt.swing.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JFileChooser;

import org.apache.log4j.Logger;

import dt.swing.DTDesktop;

/**
 * @author Michiel Kalkman
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class LoadAction extends AbstractAction {
    private static final long serialVersionUID = 5361588632986486450L;
    private static final Logger logger = Logger.getLogger( LoadAction.class);
	private final DTDesktop desktop;

	/**
	 *
	 */
	public LoadAction(final DTDesktop desktop) {
		super();
		this.desktop = desktop;
		// TODO Auto-generated constructor stub
	}
	/**
	 * @param name
	 */
	public LoadAction(final String name, final DTDesktop desktop) {
		super(name);
		this.desktop = desktop;
	}
	/**
	 * @param name
	 * @param icon
	 */
	public LoadAction(final String name, final Icon icon, final DTDesktop desktop) {
		super(name, icon);
		this.desktop = desktop;
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent actionEvent) {
		  final JFileChooser fc = new JFileChooser();

//		  fc.addChoosableFileFilter( new DTXMLFileFilter());
//
		  final int returnVal = fc.showOpenDialog( desktop);

		  //Query the JFileChooser to get the input from the user
		  if(returnVal == JFileChooser.APPROVE_OPTION) {
		    final File file = fc.getSelectedFile();

		    try {
				desktop.openNewWindow( IO.fromXML( file));
			} catch (final IOException exception) {
				logger.error( exception);
			}
		  }
	}
}
