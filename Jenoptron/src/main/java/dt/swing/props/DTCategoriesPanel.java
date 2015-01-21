package dt.swing.props;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JPanel;

import jdt.icore.IDecisionTable;

import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.forms.builder.DefaultFormBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.FormLayout;

public class DTCategoriesPanel extends JPanel {
	private static final long serialVersionUID = -8086554300748960363L;

	public DTCategoriesPanel(final IDecisionTable decisionTable) {
		super();

		add(getTablePropsComponent());
	}

	private JComponent getTablePropsComponent() {
		final JList list;
		final JButton okButton = new JButton("OK");
		final JButton cancelButton = new JButton("Cancel");

		{
			final SelectionInList selectionInList = new SelectionInList();
			list = BasicComponentFactory.createList(selectionInList);
		}

		final FormLayout layout = new FormLayout(
				"pref, 4dlu, 50dlu, 4dlu, min", "pref, 2dlu, pref, 2dlu, pref");
		final DefaultFormBuilder builder = new DefaultFormBuilder(layout);
		builder.setDefaultDialogBorder();

		builder.append("&Title:", list);
		builder.nextLine();
		builder.append(ButtonBarFactory
				.buildOKCancelBar(okButton, cancelButton), builder
				.getColumnCount());

		return builder.getPanel();
	}

}
