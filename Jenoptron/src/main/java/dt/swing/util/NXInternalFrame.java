package dt.swing.util;

import javax.swing.JInternalFrame;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import jdt.util.xmlencoder.XMLEncoderFactory;

public class NXInternalFrame extends DTInternalFrame implements InternalFrameListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2273456393553564284L;
//	private static final Logger logger = Logger.getLogger(NXInternalFrame.class);
	
	static {
		XMLEncoderFactory.add(JInternalFrame.class, getPersistenceDelegate());
	}

	public NXInternalFrame() {
		// Just for use with XMLEncoder.
		super();
	}

	public void internalFrameActivated(final InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void internalFrameClosed(final InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void internalFrameClosing(final InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void internalFrameDeactivated(final InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void internalFrameDeiconified(final InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void internalFrameIconified(final InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}

	public void internalFrameOpened(final InternalFrameEvent e) {
		// TODO Auto-generated method stub
		
	}
}
