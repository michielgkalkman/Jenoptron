package dt.swing.dnd;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;

import jdt.icore.IDecisionTable;

public class DTTableTransferable implements Transferable {
	private final IDecisionTable decisionTable;
	
	public static final DataFlavor decisionTableFlavor = new DataFlavor( IDecisionTable.class, "IDecisionTable");

	private static final DataFlavor[] dataFlavors = new DataFlavor[] { decisionTableFlavor};

	public DTTableTransferable(final IDecisionTable decisionTable) {
		this.decisionTable = decisionTable;
	}

	public Object getTransferData( final DataFlavor dataFlavor)
			throws UnsupportedFlavorException {
		if (!isDataFlavorSupported( dataFlavor)) {
			throw new UnsupportedFlavorException( dataFlavor);
		}
		
		return decisionTable;
	}

	public DataFlavor[] getTransferDataFlavors() {
		return dataFlavors;
	}

	public boolean isDataFlavorSupported( final DataFlavor dataFlavor) {
		boolean fDataFlavorIsSupported = false;
		for( DataFlavor aDataFlavor : dataFlavors ) {
			if( aDataFlavor.equals( dataFlavor)) {
				fDataFlavorIsSupported = true;
				break;
			}
		}
		return fDataFlavorIsSupported;
	}

	public IDecisionTable getDecisionTable() {
		return decisionTable;
	}
}
