package dt.swing;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

import jdt.icore.IDecisionTable;
import dt.swing.action.CopyDefaultTextAction;

public class DTTablePanel extends DTPanel {
	private static final long serialVersionUID = 5405631376452046977L;
//	private static final Logger logger = Logger.getLogger( DTTablePanel.class);

	private DTTable table;

	public DTTablePanel(final IDecisionTable decisionTable) {
		super();

		setDecisionTable( decisionTable);

		fill();

		decisionTable.addPropertyChangeListener( this);

		setVisible( true);
	}

	public final void setDecisionTable(final IDecisionTable decisionTable) {
		table = new DTTable(decisionTable);
	}


	public DTTable getTable() {
		return table;
	}


	public void setTable(final DTTable table) {
		this.table = table;
	}


	@Override
	protected void fill() {
		// TODO Auto-generated method stub
		table.setPreferredScrollableViewportSize(new Dimension(500, 70));
		table.setMinimumSize(new Dimension(600, 80));

		final CopyDefaultTextAction copyAction = new CopyDefaultTextAction(table);
		table.getInputMap().put(
				KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK),
				"copyAction");
		table.getActionMap().put("copyAction", copyAction);

		final JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane);
	}
}
