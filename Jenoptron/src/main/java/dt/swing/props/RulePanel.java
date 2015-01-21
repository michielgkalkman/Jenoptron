package dt.swing.props;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jdt.icore.IRule;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class RulePanel extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7568837432864772325L;
	private final IRule rule;
	
	public RulePanel( final IRule rule) {
		super();

		this.rule = rule;

		add( getTablePropsComponent());
	}
	
	private JComponent getTablePropsComponent() {
		final JLabel label = new JLabel( "NNNNNog niet geimplementeerd: " + rule.hashCode());
		final JButton deleteRule = new JButton( "Delete Rule");
		
        final FormLayout layout =
                new FormLayout("pref", "pref, 2dlu, pref");
        final CellConstraints cellConstraints = new CellConstraints();
        
        final PanelBuilder panelBuilder = new PanelBuilder( layout);
        panelBuilder.setDefaultDialogBorder();

        panelBuilder.add( label);
        panelBuilder.add( deleteRule, cellConstraints.xy( 1, 3));

        return panelBuilder.getPanel();
	}

	public void actionPerformed( final ActionEvent actionEvent) {
	}
}
