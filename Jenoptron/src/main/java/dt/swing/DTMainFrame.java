/*
 * Created on Aug 16, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dt.swing;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JFrame;

import jdt.util.xmlencoder.XMLEncoderFactory;

import org.apache.log4j.Logger;
import org.taHjaj.wo.swingk.util.GUIUtil;

import dt.swing.menu.DTMenuBar;

/**
 * @author Michiel Kalkman
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DTMainFrame extends JFrame implements WindowListener {
	private static final Logger logger = Logger.getLogger( DTMainFrame.class);

	private static final long serialVersionUID = 8878121951474931653L;
	private DTDesktop desktop;

	public DTMainFrame() {
		this( "JEnoptron - J\u03b5\u03bd-\u03bf\u03c0\u03c4\u03c1\u03bf\u03bd");
	}

	public DTMainFrame( final String title) {
		super( title);
		desktop = new DTDesktop();

        setJMenuBar( new DTMenuBar( desktop));

		setSize( GUIUtil.getScreenSize());

        add( desktop);

        addWindowListener( this);

        DTHelp.createHelp( getRootPane());
	}

	public DTDesktop getDesktop() {
		return desktop;
	}

	public void windowActivated(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowClosed(final WindowEvent e) {
		// TODO Auto-generated method stub
	}

	public void windowClosing(final WindowEvent e) {
		// TODO Auto-generated method stub
		logger.debug( "Done.");

		final JFrame frame = this;

//		saveGUI(frame);
	}

	public void windowDeactivated(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowDeiconified(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowIconified(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void windowOpened(final WindowEvent e) {
		// TODO Auto-generated method stub

	}

	public void setDesktop(final DTDesktop desktop) {
		this.desktop = desktop;
	}


	private static void saveGUI( final JFrame frame) {
		final File homeDirectory = new File( System.getProperty( "user.home"));

		if( homeDirectory.exists()) {
			final File jdtDirectory = new File( homeDirectory, ".jdt");

			if( ! jdtDirectory.exists()) {
				jdtDirectory.mkdir();
			}

			final File jdt = new File( jdtDirectory, "jdt.xml");


			try {
				logger.debug( "Going to write to " + jdt.getCanonicalPath());
			} catch (final IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			XMLEncoder encoder = null;
			OutputStream outputStream = null;
			try {
				outputStream = new BufferedOutputStream( new FileOutputStream( jdt));
				encoder = XMLEncoderFactory.getXMLEncoder( outputStream);

//				encoder.writeObject( frame);
			} catch (final FileNotFoundException fileNotFoundException) {
				// TODO Auto-generated catch block
				logger.error( "Something shitty happened while writing .jdt/jdt.xml to your home dir.", fileNotFoundException);
			} finally {
				if( encoder != null) {
					encoder.close();
				}
				if( outputStream != null) {
					try {
						outputStream.close();
					} catch ( final IOException exception) {
						logger.error( "An exception happened while closing the outputstream", exception);
					}
				}
			}

		}
	}



}
