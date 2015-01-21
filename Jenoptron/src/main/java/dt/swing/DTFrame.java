/*
 * Created on Jul 28, 2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package dt.swing;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JInternalFrame;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import jdt.core.DecisionTable;
import jdt.icore.IDecisionTable;
import jdt.util.xmlencoder.XMLEncoderFactory;

import org.apache.log4j.Logger;

import com.jgoodies.binding.beans.BeanAdapter;

import dt.swing.util.DTInternalFrame;

/**
 * @author Michiel Kalkman
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class DTFrame extends DTInternalFrame implements InternalFrameListener, PropertyChangeListener {
    private static final long serialVersionUID = -2513679617638960547L;
    private static final Logger logger = Logger.getLogger( DTFrame.class);

    static {
    	XMLEncoderFactory.add( JInternalFrame.class, getPersistenceDelegate());
    }

    public DTFrame() {
    	this( new DecisionTable());
	}

	public DTFrame( final String title) {
		this( new DecisionTable( title));
	}

	public DTFrame( final IDecisionTable decisionTable) {
		super( decisionTable.getShortDescription(), true, true, true, true);

		{
			final BeanAdapter beanAdapter = new BeanAdapter( decisionTable);
			beanAdapter.addPropertyChangeListener( this);
			beanAdapter.addPropertyChangeListener( IDecisionTable.PROP_SHORT_DESCRIPTION, this);
		}

		this.decisionTable = decisionTable;

		setMinimumSize( new Dimension( 300, 300));
		setPreferredSize( new Dimension( 300, 300));
		setSize( new Dimension( 300, 300));

        addInternalFrameListener(this);

//		 Create a left-right split pane
	    final JSplitPane hpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, null, null);

	    final DTPanel leftPanel = new DTTablePanel( decisionTable);
	    final DTPanel rightPanel = new DTTablePanel( decisionTable);

	    hpane.setLeftComponent( leftPanel);
	    hpane.setRightComponent( rightPanel);

	    decisionTable.addPropertyChangeListener(
	    		new PropertyChangeListener() {
			    	public void propertyChange( final PropertyChangeEvent propertyChangeEvent) {
			    		leftPanel.setVisible(false);
			    		leftPanel.setVisible(true);
			    		logger.debug( "repaint LeftPanel !");
				    }
			    }
		    );

	    decisionTable.addPropertyChangeListener(
	    				new PropertyChangeListener() {
			    	public void propertyChange( final PropertyChangeEvent propertyChangeEvent) {
			    		leftPanel.setVisible(false);
			    		leftPanel.setVisible(true);
			    		logger.debug( "repaint rightPanel !");
				    }
			    }
		    );

	    hpane.setDividerLocation(50);
	    add( hpane);

        setVisible( true);

        decisionTable.addPropertyChangeListener( this);
	}

	private IDecisionTable decisionTable;

	public IDecisionTable getDecisionTable() {
		return decisionTable;
	}

	public void setDecisionTable(final IDecisionTable decisionTable) {
		this.decisionTable = decisionTable;
	}

	public void propertyChange( final PropertyChangeEvent evt) {
	      SwingUtilities.invokeLater(new Runnable() {
		        public void run()
		        {
		        	if( IDecisionTable.PROP_SHORT_DESCRIPTION.equals( evt.getPropertyName())) {
		        		setTitle( (String) evt.getNewValue());
		        	}
		        }});
	}

	public void internalFrameActivated(final InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}

	public void internalFrameClosed(final InternalFrameEvent e) {
		// TODO Auto-generated method stub
		logger.debug( "Closed");
	}

	public void internalFrameClosing(final InternalFrameEvent e) {
		// TODO Auto-generated method stub
		logger.debug( "Closing");
	}

	public void internalFrameDeactivated(final InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}

	public void internalFrameDeiconified(final InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}

	public void internalFrameIconified(final InternalFrameEvent e) {
		// TODO Auto-generated method stub

	}

	public void internalFrameOpened(final InternalFrameEvent e) {
		// TODO Auto-generated method stub
		setLocation( getLocation());
	}

}
