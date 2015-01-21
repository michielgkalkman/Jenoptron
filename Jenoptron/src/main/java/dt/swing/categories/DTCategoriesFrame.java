package dt.swing.categories;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import jdt.core.binary.BinaryCondition;
import jdt.core.category.ExclusiveConditionsGroup;
import jdt.icore.IDecisionTable;

import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.FormLayout;

import dt.swing.util.DTInternalFrame;

public class DTCategoriesFrame extends DTInternalFrame implements ActionListener {
	private static final long serialVersionUID = -750936473979811404L;
    private final JButton okButton = new JButton("OK");
	private final JTextField titleTextField = new JTextField( 12);
	private final JTextArea textArea = new JTextArea(5, 20);
	private final IDecisionTable decisionTable;
	
	public DTCategoriesFrame( final DTCategoriesBean categoriesBean) {
		super("Categories: " + categoriesBean.getDecisionTable().getShortDescription(), true, true,
				true, true);
		
		decisionTable = categoriesBean.getDecisionTable();
		
		add( getTablePropsComponent());

		setVisible(true);
	}
	
	private JComponent getTablePropsComponent() {
        okButton.addActionListener( this);
        final JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener( this);

        final FormLayout layout =
                new FormLayout("l:p, 2dlu, p, 2dlu, p, 2dlu, p, 2dlu, p:g", "");
        final DefaultFormBuilder builder = new DefaultFormBuilder(layout);
        builder.setDefaultDialogBorder();

        builder.append("&Title:", titleTextField, 7);
        builder.nextLine();
        builder.append( "&Conditions", textArea, 7);
        
        builder.nextLine();
        builder.append(ButtonBarFactory.buildOKCancelBar(okButton, cancelButton),
                builder.getColumnCount());

        return builder.getPanel();
	}

	public void actionPerformed( final ActionEvent actionEvent) {
		if( actionEvent.getSource().equals( okButton) ) {
			final ExclusiveConditionsGroup exclusiveConditionsGroup = 
					new ExclusiveConditionsGroup( titleTextField.getText());

			final String textAString = textArea.getText();
			
			final StringTokenizer stringTokenizer = new StringTokenizer( textAString, "\n");
			
			while( stringTokenizer.hasMoreTokens()) {
				final String category = stringTokenizer.nextToken();
				exclusiveConditionsGroup.add( new BinaryCondition( category));
			}
			
			decisionTable.add( exclusiveConditionsGroup);
		}
		
		this.dispose();
	}
	
	
}
