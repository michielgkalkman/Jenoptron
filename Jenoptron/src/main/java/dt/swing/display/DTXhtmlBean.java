package dt.swing.display;

import java.beans.PropertyChangeListener;

import com.jgoodies.binding.beans.ExtendedPropertyChangeSupport;

public class DTXhtmlBean implements IDisplayOptionBean {
	private boolean isShownAsText;
	public static final String PROP_SHOWN_AS_TEXT ="isShownAsText";
	
	public DTXhtmlBean() {
		super();
	}
	
	private final ExtendedPropertyChangeSupport extendedPropertyChangeSupport =
		new ExtendedPropertyChangeSupport( this);

	public void addPropertyChangeListener( final PropertyChangeListener propertyChangeListener) {
		extendedPropertyChangeSupport.addPropertyChangeListener( propertyChangeListener);
	}

	public void removePropertyChangeListener( final PropertyChangeListener propertyChangeListener) {
		extendedPropertyChangeSupport.removePropertyChangeListener( propertyChangeListener);
	}

	public boolean getIsShownAsText() {
		return isShownAsText;
	}

	public void setIsShownAsText(final boolean isShownAsText) {
		final boolean oldShownAsText = this.isShownAsText;
		this.isShownAsText = isShownAsText;
		extendedPropertyChangeSupport.firePropertyChange( PROP_SHOWN_AS_TEXT, oldShownAsText, isShownAsText);
	}
}
