package dt.swing.dnd;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import jdt.icore.IDecisionTable;

import org.apache.log4j.Logger;

import dt.swing.DTDesktop;

public class DTDesktopTransferHandler extends TransferHandler {
	private static final long serialVersionUID = -2720746417697084789L;
	private static final Logger logger = Logger.getLogger( DTDesktopTransferHandler.class);

	private final DTDesktop desktop;
	
	public DTDesktopTransferHandler( final DTDesktop desktop) {
		super();
		this.desktop = desktop;
	}
	
	@Override
	public boolean importData( final JComponent component, final Transferable transferable) {
		final DataFlavor[] dataFlavors = transferable.getTransferDataFlavors();
		
		boolean fInserted = false;
		
		if (canImport( component, dataFlavors)) {
			for ( DataFlavor dataFlavor : dataFlavors) {
				if( dataFlavor.equals( DTTableTransferable.decisionTableFlavor)) {
					try {
						final IDecisionTable decisionTable = (IDecisionTable) transferable.getTransferData( dataFlavor);
						
						desktop.openNewWindow( decisionTable);
						
						fInserted = true;
						
						break;
					} catch ( final UnsupportedFlavorException unsupportedFlavorException) {
						final String errorMsg = "Error while importing data from ";
						logger.error( errorMsg, unsupportedFlavorException);
						throw new RuntimeException( errorMsg, unsupportedFlavorException);
					} catch ( final IOException exception) {
						final String errorMsg = "Error while importing data from ";
						logger.error( errorMsg, exception);
						throw new RuntimeException( errorMsg, exception);
					} catch ( final RuntimeException runtimeException) {
						final String errorMsg = "Error while importing data from ";
						logger.error( errorMsg, runtimeException);
						throw new RuntimeException( errorMsg, runtimeException);
					}
            	}
            }
        }

        return fInserted;
    }
    
	@Override
    public boolean canImport( final JComponent c, final DataFlavor[] dataFlavors) {
		boolean fWillImportData = false;
        for ( DataFlavor dataFlavor : dataFlavors) {
        	if( dataFlavor.equals( DTTableTransferable.decisionTableFlavor)) {
        		logger.debug( "DecisionTableFlavor !");
        		fWillImportData = true;
        		break;
        	}
        }
        
        return fWillImportData;
    }
    

}
