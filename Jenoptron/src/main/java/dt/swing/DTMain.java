/*
 * Created on Jul 28, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dt.swing;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import javax.swing.RepaintManager;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.taHjaj.wo.swingk.util.CheckThreadViolationRepaintManager;




/**
 * @author Michiel Kalkman
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DTMain {
	private static DTMainFrame mainFrame;
	private static final Logger logger = Logger.getLogger( DTMain.class);

	private DTMain() {
		super();
	}

	public static void main(final String[] args) {
		BasicConfigurator.configure();
		logger.debug("start");

        try {
			SwingUtilities.invokeAndWait(new Runnable() {
			    public void run() {
					RepaintManager.setCurrentManager( new CheckThreadViolationRepaintManager());

					try {
//						   UIManager.setLookAndFeel(new PlasticLookAndFeel());
					} catch ( final Exception exception) {
						final String errorMessage = "No Plastic L&F available";
						logger.error( errorMessage, exception);
					}


					mainFrame = createMainFrame();
					mainFrame.setVisible( true);
			    }
			});
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

//	private void displayException() {
//		// TODO: een popup met stacktrace.
//	}

	private static DTMainFrame createMainFrame() {
		final File homeDirectory = new File( System.getProperty( "user.home"));

		DTMainFrame mainFrame = null;

//		if( homeDirectory.exists()) {
//			final File jdtDirectory = new File( homeDirectory, ".jdt");
//
//			if( jdtDirectory.exists()) {
//				final File jdt = new File( jdtDirectory, "jdt.xml");
//
//				if( jdt.exists()) {
//					XMLDecoder decoder;
//					try {
//						decoder = new XMLDecoder(
//							new BufferedInputStream( new FileInputStream( jdt)));
//
////						mainFrame = (DTMainFrame) decoder.readObject();
//					} catch (final FileNotFoundException e) {
//						try {
//							logger.debug( "Could not read: " + jdt.getCanonicalPath());
//						} catch (final IOException e1) {
//							// TODO Auto-generated catch block
//							logger.debug( "Big problem with writing your .jdt/jdt.xml into your home directory");
//							e1.printStackTrace();
//						}
//						System.exit(1);
//					} catch( final RuntimeException runtimeException) {
//						try {
//							logger.debug( "Could not read: " + jdt.getCanonicalPath());
//						} catch (final IOException e1) {
//							// TODO Auto-generated catch block
//							logger.debug( "Big problem with writing your .jdt/jdt.xml into your home directory");
//							e1.printStackTrace();
//						}
//						runtimeException.printStackTrace();
//					}
//				}
//
//			} else {
//				jdtDirectory.mkdir();
//			}
//
//		}

		if( mainFrame == null) {
			mainFrame = new DTMainFrame();
		}

		return mainFrame;
	}

	public static DTMainFrame getMainFrame() {
		synchronized ( mainFrame) {
			return mainFrame;
		}
	}
}
