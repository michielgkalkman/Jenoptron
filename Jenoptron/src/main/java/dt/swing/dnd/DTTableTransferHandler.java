package dt.swing.dnd;

import java.awt.datatransfer.Transferable;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import jdt.icore.IDecisionTable;

import org.apache.log4j.Logger;

import dt.generators.GeneratorFactorySPI;
import dt.generators.TextGenerator;
import dt.swing.DTTable;
import dt.swing.action.ActionUtil;

public class DTTableTransferHandler extends TransferHandler {
    /**
	 *
	 */
	private static final long serialVersionUID = 8369475751551272137L;
	private static final Logger logger = Logger.getLogger( DTTableTransferHandler.class);

	private final TextGenerator generator;

	public DTTableTransferHandler() {
		super();
		generator = GeneratorFactorySPI.getAnyTextGenerator();
	}

	@Override
	protected Transferable createTransferable( final JComponent component) {
		try {
			logger.debug( "Creating a DTTableTransferHandler");

			final DTTable table = (DTTable) component;
			final IDecisionTable sourceDecisionTable = table.getDecisionTable();

			logger.debug( "Before:\n" + generator.getText( sourceDecisionTable));

			final IDecisionTable decisionTable = sourceDecisionTable.deepcopy();

			logger.debug( "After:\n" + generator.getText( decisionTable));

			ActionUtil.deleteUnselectedRows( table.getSelectedRows(), decisionTable);

			logger.debug( "After:\n" + generator.getText( decisionTable));


			final DTTableTransferable tableTransferable =
					new DTTableTransferable( decisionTable);

			return tableTransferable;
		} catch ( final RuntimeException runtimeException) {
			final String errorMsg = "Error while creating something transferable";
			logger.error( errorMsg, runtimeException);
			throw runtimeException;
		}
	}

	@Override
	public int getSourceActions( final JComponent component) {
        return COPY_OR_MOVE;
    }

}

