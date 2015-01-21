package dt.swing.display;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import org.taHjaj.wo.events.ObservableMediator;

import jdt.util.xmlencoder.XMLEncoderFactory;
import dt.swing.DTPanel;
import dt.swing.util.DTInternalFrame;

public class DTDisplay extends DTInternalFrame implements InternalFrameListener {
	/**
	 *
	 */
	private static final long serialVersionUID = 4041776684252715643L;

	static {
		XMLEncoderFactory.add(JInternalFrame.class, getPersistenceDelegate());
	}

	public DTDisplay() {
		// Just for use with XMLEncoder.
		super();
	}

	public DTDisplay(final DTDisplayBean displayBean) {
		super("Properties: " + displayBean.getDecisionTable().getShortDescription(), true, true,
				true, true);

		setLayout(new GridLayout(1, 0));

		final JPanel leftPanel = new JPanel();

		{
			final JPanel optionPanel = new DTDisplayOptionsPanel( displayBean);
			final JPanel detailPanel = new GeneratorPropertiesPanel( displayBean.getCurrentGenerator().getGeneratorOptions() );
			leftPanel.setLayout( new BorderLayout());
			leftPanel.add( optionPanel, BorderLayout.NORTH);
			leftPanel.add( detailPanel, BorderLayout.SOUTH);
		}

		final DTPanel displayPanel = new DTDisplayPanel( displayBean);

		
		ObservableMediator.getInstance().register(displayBean, displayPanel);

		// Add the scroll panes to a split pane.
		final JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		splitPane.setLeftComponent( leftPanel);
		splitPane.setRightComponent( new JScrollPane( displayPanel));

		// Add the split pane to this panel.
		add(splitPane);

		addInternalFrameListener(this);

		setMinimumSize( getPreferredSize());
		pack();
	}

	public void internalFrameOpened(final InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}

	public void internalFrameClosing(final InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}

	public void internalFrameClosed(final InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}

	public void internalFrameIconified(final InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}

	public void internalFrameDeiconified(final InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}

	public void internalFrameActivated(final InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}

	public void internalFrameDeactivated(final InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}
}
