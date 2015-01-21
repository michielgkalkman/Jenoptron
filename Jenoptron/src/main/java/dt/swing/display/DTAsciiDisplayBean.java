package dt.swing.display;

import java.beans.PropertyChangeListener;

import com.jgoodies.binding.beans.ExtendedPropertyChangeSupport;

public class DTAsciiDisplayBean implements IDisplayOptionBean {
	private boolean displayingLines;
	private boolean wrappingTexts;
	public static final String PROP_DISPLAYING_LINES = "displayingLines";
	public static final String PROP_WRAPPING_TEXTS = "wrappingTexts";

	public DTAsciiDisplayBean() {
		super();
	}
	
	private final ExtendedPropertyChangeSupport extendedPropertyChangeSupport =
		new ExtendedPropertyChangeSupport( this);

	public boolean isDisplayingLines() {
		return displayingLines;
	}
		
	public void setDisplayingLines(final boolean displayingLines) {
		final boolean oldDisplayingLines = this.displayingLines;
		this.displayingLines = displayingLines;
		extendedPropertyChangeSupport.firePropertyChange( PROP_DISPLAYING_LINES, oldDisplayingLines, displayingLines);
	}

	public boolean isWrappingTexts() {
		return wrappingTexts;
	}
	
	public void setWrappingTexts( final boolean fWrappingTexts) {
		final boolean oldWrappingTexts = this.wrappingTexts;
		this.wrappingTexts = fWrappingTexts;
		extendedPropertyChangeSupport.firePropertyChange( PROP_WRAPPING_TEXTS, oldWrappingTexts, fWrappingTexts);
	}

	public void addPropertyChangeListener( final PropertyChangeListener propertyChangeListener) {
		extendedPropertyChangeSupport.addPropertyChangeListener( propertyChangeListener);
	}

	public void removePropertyChangeListener( final PropertyChangeListener propertyChangeListener) {
		extendedPropertyChangeSupport.removePropertyChangeListener( propertyChangeListener);
	}

}
