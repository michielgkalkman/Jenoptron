package dt.swing.props;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import jdt.icore.IAction;

import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.beans.BeanAdapter;
import com.jgoodies.binding.value.ValueModel;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.FormLayout;

public class DTActionProps extends JPanel implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7568837432864772325L;
	private JTextField titleTextField;
	private final IAction action;
	
	public DTActionProps( final IAction action) {
		super();

		this.action = action;

		add( getTablePropsComponent());
	}
	
	private JComponent getTablePropsComponent() {
		final BeanAdapter beanAdapter = new BeanAdapter( action, true);
		
		{
			final ValueModel valueModel = beanAdapter.getValueModel( IAction.PROP_SHORT_DESCRIPTION);
			titleTextField =
				BasicComponentFactory.createTextField(valueModel);
		}
		final JTextArea textArea = new JTextArea(5, 20);
        final JButton okButton = new JButton("OK");
        final JButton cancelButton = new JButton("Cancel");
        okButton.addActionListener( this);
        cancelButton.addActionListener( this);

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
		action.setShortDescription( titleTextField.getText());
	}
}
