/*
 * Created on Aug 1, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dt.swing.action;

import java.awt.event.ActionEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.Icon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import jdt.icore.IDecisionTable;
import dt.generators.Generator;
import dt.generators.GeneratorFactorySPI;
import dt.swing.DTTable;
import dt.swing.filefilters.DTFileFilter;

/**
 * @author Michiel Kalkman
 *
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class SaveAsAction extends AbstractAction {
	private static final long serialVersionUID = 7216568883982331309L;

	private final DTTable table;

	/**
	 *
	 */
	public SaveAsAction(final DTTable table) {
		super();
		this.table = table;
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param name
	 */
	public SaveAsAction(final String name, final DTTable table) {
		super(name);
		this.table = table;
	}

	/**
	 * @param name
	 * @param icon
	 */
	public SaveAsAction(final String name, final Icon icon, final DTTable table) {
		super(name, icon);
		this.table = table;
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(final ActionEvent e) {
		final IDecisionTable decisionTable = table.getDecisionTable();

		final JFileChooser fc = new JFileChooser(decisionTable.getFile());

		for( final Generator generator : GeneratorFactorySPI.getAllFileGenerators()) {
			fc.addChoosableFileFilter( new DTFileFilter( generator));
		}

		final int returnVal = fc.showSaveDialog(table);

		// Query the JFileChooser to get the input from the user
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			// declare the file object
			final File selectedFile = fc.getSelectedFile();

			final DTFileFilter fileFilter = (DTFileFilter) fc.getFileFilter();

			final File newFile = fileFilter.getCorrectlyExtendedFile(selectedFile);

			if (newFile.exists()) {
				// TODO: do you wish to overwrite ...
				final int n = JOptionPane.showConfirmDialog(table,
						"Overwrite existing file " + newFile.getName() + " ?",
						"About to overwrite existing file",
						JOptionPane.YES_NO_OPTION);

				if (n != JOptionPane.NO_OPTION) {
					fileFilter.save(newFile, decisionTable.deepcopy()
							.reduce());
				}
			}

			decisionTable.setFile( newFile);
		}
	}
}
