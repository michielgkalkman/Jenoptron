package dt.swing.display;

import java.awt.GridLayout;
import java.beans.PropertyChangeListener;

import javax.swing.JScrollPane;

import org.taHjaj.wo.events.Observable;
import org.taHjaj.wo.events.ObservableMediator;
import org.taHjaj.wo.swingk.KPanel;

public class PropertySheetPanel extends KPanel implements PropertyChangeListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1802887347979392315L;

	private final PropertySheet propertySheet;
	
	public PropertySheetPanel( final Observable observable) {
		super(new GridLayout(1, 0));

		this.propertySheet = new PropertySheet( new PropertyList( observable));
		
		fill();
		
//		ObservableMediator.getInstance().register( observable, this);
	}

	@Override
	protected void fill() {
		final JScrollPane scrollPane = new JScrollPane( propertySheet);
		add(scrollPane);
	}

}
