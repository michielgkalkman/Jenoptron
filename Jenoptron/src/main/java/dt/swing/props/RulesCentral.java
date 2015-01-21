package dt.swing.props;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jdt.core.category.ExclusiveConditionsGroup;
import jdt.icore.IDecisionTable;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class RulesCentral extends JPanel implements ActionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7869076279080734717L;
	private final IDecisionTable decisionTable;
	
	public RulesCentral( final IDecisionTable decisionTable) {
		super();

		this.decisionTable = decisionTable;
		
		add( getTablePropsComponent());
	}
	
	private JComponent getTablePropsComponent() {
		final JLabel label = new JLabel( "NNNNNog niet geimplementeerd: ");
		final JButton addRule = new JButton( "Add Rule");
		
        final FormLayout layout =
                new FormLayout("pref", "pref, 2dlu, pref");
        final CellConstraints cellConstraints = new CellConstraints();
        
        final PanelBuilder panelBuilder = new PanelBuilder( layout);
        panelBuilder.setDefaultDialogBorder();

        panelBuilder.add( label);
        panelBuilder.add( addRule, cellConstraints.xy( 1, 3));

        return panelBuilder.getPanel();
	}

	public void actionPerformed( final ActionEvent e) {
		decisionTable.add( new ExclusiveConditionsGroup( "Group " + decisionTable.getRules().size() ));
	}
}
