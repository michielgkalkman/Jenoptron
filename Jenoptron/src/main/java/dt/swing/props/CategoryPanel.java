package dt.swing.props;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jdt.core.category.IGroup;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class CategoryPanel extends JPanel implements ActionListener {
	private static final long serialVersionUID = -7568837432864772325L;
	private final IGroup group;
	
	public CategoryPanel( final IGroup group) {
		super();

		this.group = group;

		add( getTablePropsComponent());
	}
	
	private JComponent getTablePropsComponent() {
		final JLabel label = new JLabel( "NNNNNog niet geimplementeerd: " + group.hashCode());
		final JButton deleteRule = new JButton( "Delete Category");
		
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
