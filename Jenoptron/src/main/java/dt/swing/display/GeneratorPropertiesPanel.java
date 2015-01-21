package dt.swing.display;

import java.beans.PropertyChangeListener;

import org.apache.log4j.Logger;
import org.taHjaj.wo.events.Observable;
import org.taHjaj.wo.events.ObservableMediator;

import dt.swing.DTPanel;

public class GeneratorPropertiesPanel extends DTPanel implements PropertyChangeListener {
	/**
	 *
	 */
	private static final long serialVersionUID = -1261737782899126682L;
	private static final Logger logger = Logger.getLogger( GeneratorPropertiesPanel.class);
	private final Observable observable;
	
	public GeneratorPropertiesPanel( final Observable observable) {
		super();
		
		this.observable = observable;
		
	    fill();
		
//		ObservableMediator.getInstance().register( observable, this);
	}

	@Override
	protected void fill() {
		logger.debug( "Fill:DTDisplayDetailsPanel");

		final PropertySheetPanel propertySheetPanel = new PropertySheetPanel( observable);

	    add( propertySheetPanel);
	}
}
