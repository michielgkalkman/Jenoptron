package dt.swing.dnd;

import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import org.apache.log4j.Logger;

public class DTDesktopMouseAdapter extends MouseAdapter {
	
	public DTDesktopMouseAdapter() {
		super();
	}
	
//	@Override
//	public void mouseExited(MouseEvent arg0) {
//      super.mouseExited(arg0);
//	}

//	@Override
//	public void mouseMoved(MouseEvent arg0) {
//		// TODO Auto-generated method stub
//		super.mouseMoved(arg0);
//	}

//	@Override
//	public void mousePressed(MouseEvent arg0) {
//		// TODO Auto-generated method stub
//		super.mousePressed(arg0);
//	}

//	@Override
//	public void mouseWheelMoved(MouseWheelEvent arg0) {
//		// TODO Auto-generated method stub
//		super.mouseWheelMoved(arg0);
//	}

	private static final Logger logger = Logger.getLogger( DTDesktopMouseAdapter.class);

	@Override
	public void mouseReleased( final MouseEvent mouseEvent) {
		logger.debug( "Mouse released on desktop");
        final JComponent component = (JComponent)mouseEvent.getSource();
        final TransferHandler handler = component.getTransferHandler();
        handler.importData( component,  new StringSelection( "lala"));
	}

//	@Override
//	public void mouseDragged(MouseEvent arg0) {
//		// TODO Auto-generated method stub
//		super.mouseDragged(arg0);
//	}
}


