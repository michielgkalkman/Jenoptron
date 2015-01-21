package dt.swing.props;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.layout.FormLayout;

public class DTDefaultProps extends JPanel {
	private static final long serialVersionUID = -7869076279080734717L;
	private final String componentName;
	public DTDefaultProps( final String componentName) {
		super();
		this.componentName = componentName;

		add( getTablePropsComponent());
	}
	
	private JComponent getTablePropsComponent() {
		final JLabel label = new JLabel( "Nog niet geimplementeerd: " + componentName);
		
        final FormLayout layout =
                new FormLayout("l:p", "");
        final DefaultFormBuilder builder = new DefaultFormBuilder(layout);
        builder.setDefaultDialogBorder();

        builder.append( label);

        return builder.getPanel();
	}

}
