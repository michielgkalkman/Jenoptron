package dt.swing.props;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.beans.BeanAdapter;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.FormLayout;

import jdt.icore.IDecisionTable;

public class DTDecisionTableProps extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3316953706783617170L;
	private JTextField titleTextField;
	private final IDecisionTable decisionTable;
	
	public DTDecisionTableProps( final IDecisionTable decisionTable) {
		super();

		this.decisionTable = decisionTable;

		add( getTablePropsComponent());
	}
	
	private JComponent getTablePropsComponent() {
		final BeanAdapter beanAdapter = new BeanAdapter( decisionTable, true);
		
		{
			final ValueModel valueModel = beanAdapter.getValueModel( IDecisionTable.PROP_SHORT_DESCRIPTION);
			titleTextField =
				BasicComponentFactory.createTextField(valueModel);
				// new JTextField( decisionTable.getShortDescription(), 20);
		}
		final JTextArea textArea = new JTextArea(5, 20);
        final JButton okButton = new JButton("OK");
        final JButton cancelButton = new JButton("Cancel");

        final FormLayout layout =
                new FormLayout("l:p, 2dlu, p, 2dlu, p, 2dlu, p, 2dlu, p:g", "");
        final DefaultFormBuilder builder = new DefaultFormBuilder(layout);
        builder.setDefaultDialogBorder();

        builder.append("&Title:", titleTextField, 7);
        builder.nextLine();
        builder.append( "&Description", textArea, 7);
        
        builder.nextLine();
        builder.append(ButtonBarFactory.buildOKCancelBar(okButton, cancelButton),
                builder.getColumnCount());

        return builder.getPanel();
	}

	public void actionPerformed(final ActionEvent e) {
		decisionTable.setShortDescription( titleTextField.getText());
	}
}
