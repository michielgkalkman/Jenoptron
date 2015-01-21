package dt.swing.dnd;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.TransferHandler;

import dt.swing.DTTable;

public class DTTableDragMouseAdapter extends MouseAdapter implements
		MouseMotionListener {

	public DTTableDragMouseAdapter() {
		super();
	}
	
	@Override
	public void mouseDragged(final MouseEvent mouseEvent) {
		final DTTable table = (DTTable) mouseEvent.getSource();
		final TransferHandler handler = table.getTransferHandler();
		handler.exportAsDrag(table, mouseEvent, TransferHandler.COPY);
	}
}
