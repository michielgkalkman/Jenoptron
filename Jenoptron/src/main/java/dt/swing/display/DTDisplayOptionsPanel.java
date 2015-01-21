package dt.swing.display;

import java.util.List;

import javax.swing.JComboBox;

import org.taHjaj.wo.events.ObservableMediator;

import com.jgoodies.binding.adapter.BasicComponentFactory;
import com.jgoodies.binding.beans.BeanAdapter;
import com.jgoodies.binding.list.SelectionInList;
import com.jgoodies.binding.value.ValueModel;

import dt.swing.DTPanel;


public class DTDisplayOptionsPanel extends DTPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = -5930771951401558187L;

	private static final String EDITOR_ROW_SPEC =
        "p, 3dlu, p, 3dlu, p, 3dlu, p";

	private final  DTDisplayBean displayBean;

	public DTDisplayOptionsPanel( final DTDisplayBean displayBean) {
		super();

		this.displayBean = displayBean;

		fill();

		
		ObservableMediator.getInstance().register( displayBean, this);
	}


	@Override
	protected void fill() {
		final BeanAdapter beanAdapter = new BeanAdapter( displayBean);

		final ValueModel selectionHolder = beanAdapter.getValueModel( DTDisplayBean.PROP_DISPLAY_MODE);

		final List displayOptions = displayBean.getDisplayOptions();
		final SelectionInList selectionInList = new SelectionInList( displayOptions, selectionHolder);

		final JComboBox comboBox = BasicComponentFactory.createComboBox(selectionInList);

		builder.setDefaultDialogBorder();

		builder.append("&Option:", comboBox);
		builder.nextLine();
		builder.nextLine();

		add( builder.getPanel());
	}
}
